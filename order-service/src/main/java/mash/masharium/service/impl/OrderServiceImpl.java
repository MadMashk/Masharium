package mash.masharium.service.impl;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mash.masharium.api.bonus.constant.BonusServiceConfigParameters;
import mash.masharium.api.order.constant.OrderStatus;
import mash.masharium.api.order.request.ChangeOrderStatusDto;
import mash.masharium.api.order.request.OrderRequestDto;
import mash.masharium.api.order.request.PositionRequestDto;
import mash.masharium.api.order.response.OrderResponseDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.dto.OrderExpandedBonusInfoResponseDto;
import mash.masharium.entity.Order;
import mash.masharium.entity.Position;
import mash.masharium.exception.model.ForbiddenException;
import mash.masharium.exception.model.NotFoundException;
import mash.masharium.mapper.OrderMapper;
import mash.masharium.repository.OrderRepository;
import mash.masharium.scheduling.CancelOrderJob;
import mash.masharium.scheduling.SchedulerService;
import mash.masharium.service.*;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final PositionService positionService;

    private final OrderMapper orderMapper;

    private final SchedulerService schedulerService;

    private final BonusService bonusService;

    private final KitchenService kitchenService;

    private final RestaurantService restaurantService;

    @Override
    @Transactional
    public void createOrUpdate(OrderRequestDto dto) {
        Order order = getOrder(dto);
        Map<UUID, Integer> quantities = dto.getPositions()
                .stream()
                .collect(Collectors.toMap(PositionRequestDto::getId, PositionRequestDto::getQuantity));

        order.getPositions().addAll(positionService.calculateNewVolumeOfAlreadyExistPositionsAndGetNewPositions(dto.getPositions(), order));

        orderRepository.save(order);

        calculatePrice(order);
        createQuartzTrigger(order);
        //списываем ингридиенты для блюд
        restaurantService.writeOffComponentsOfPositions(order, quantities);
    }

    @Override
    @Transactional
    public List<PositionResponseDto> remove(OrderRequestDto dto) {
        Order order = orderRepository.findByClientIdAndStatus(dto.getClientId(), OrderStatus.DRAFT.name()).orElseThrow(
                getNotFoundExceptionDueToClientSupplier(dto)
        );

        List<PositionResponseDto> positionResponseDtos = positionService.subtract(dto.getPositions(), order);

        orderRepository.save(order);
        calculatePrice(order);

        //возвращаем ингридиенты удаленных блюд на склад
        restaurantService.accrualComponentsOfPositions(order, positionResponseDtos);
        return positionResponseDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllByClient(UUID clientId) {
        return orderMapper.toListDto(orderRepository.findAllByClientId(clientId));
    }

    @Override
    @Transactional
    public void changeStatus(ChangeOrderStatusDto dto) {
        //переделать на свитч кейс
        Order order = orderRepository.findById(dto.getOrderId()).orElseThrow(
                getNotFoundExceptionDueToOrderSupplier(dto.getOrderId())
        );
        actAdditionalActionsDependingOnTheOrderStatus(dto, order);
        order.setStatus(dto.getOrderStatus());
        order.setLastModified(LocalDateTime.now());
        orderRepository.save(order);
    }

    @Override
    @Transactional
    public void closeOrder(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                getNotFoundExceptionDueToOrderSupplier(orderId)
        );
        if (order.getStatus().equals(OrderStatus.DRAFT)) {
            order.setStatus(OrderStatus.CLOSED);
            order.setEndTime(LocalDateTime.now());
            orderRepository.save(order);
            restaurantService.accrualComponentsOfPositions(order);
        }
    }

    @Override
    @Transactional
    public void pay(UUID orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(getNotFoundExceptionDueToOrderSupplier(orderId));
        BigDecimal bonusPercent = Objects.nonNull(order.getClientId()) ? getBonusPercent() : BigDecimal.ZERO;
        Integer totalAmountOfBonusesToAccrue = order.getTotalPrice().multiply(bonusPercent).intValue();
        removeQuartzTrigger(order);
        if (Objects.nonNull(order.getClientId())) {
            bonusService.accrual(order, totalAmountOfBonusesToAccrue);
        }
        order.setLastModified(LocalDateTime.now());
        order.setIsPaid(true);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderExpandedBonusInfoResponseDto getPaymentInfo(UUID orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(getNotFoundExceptionDueToOrderSupplier(orderId));
        OrderExpandedBonusInfoResponseDto responseDto = orderMapper.toDto(order);
        BigDecimal bonusPercent = Objects.nonNull(order.getClientId()) ? getBonusPercent() : BigDecimal.ZERO;
        responseDto.setBonusesForOrder(order.getTotalPrice().multiply(bonusPercent).intValue());
        return responseDto;
    }

    @Override
    @Transactional
    public void spendBonuses(UUID orderId, Integer amount) {
        Order order = orderRepository.findById(orderId).orElseThrow(getNotFoundExceptionDueToOrderSupplier(orderId));
        if (Objects.isNull(order.getClientId())) {
            throw new ForbiddenException("Доступно только авторизованным пользователям");
        }
            Integer bonusesInAccount = bonusService.getAccount(order.getClientId()).getBalance();
            if (amount > bonusesInAccount) {
                amount = bonusesInAccount;
            }
            if (amount > order.getTotalPrice().intValue()) {
                amount = order.getTotalPrice().intValue();
            }
            order.setTotalPrice(order.getTotalPrice().subtract(BigDecimal.valueOf(amount)));
            order.setAppliedBonuses(order.getAppliedBonuses().add(BigDecimal.valueOf(amount)));
            bonusService.writeOff(order, amount);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> getAllActive() {
        return orderMapper.toListDto(orderRepository.findAllActive());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<LocalDate, BigDecimal> getVolumes(LocalDate firstDate, LocalDate secondDate) {
        Map<LocalDate, BigDecimal> result = new TreeMap<>();
        orderRepository.findAllPaidAndCompletedBetweenDates(firstDate.atStartOfDay(), secondDate.plusDays(1).atStartOfDay())
                .forEach(order -> {
                    LocalDateTime endTime = order.getEndTime();
                    LocalDate localDate = LocalDate.of(endTime.getYear(), endTime.getMonth(), 1);
                    if (result.containsKey(localDate)) {
                        result.put(localDate, result.get(localDate).add(order.getTotalPrice()));
                    } else {
                        result.put(localDate, order.getTotalPrice());
                    }
                });
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<UUID, Integer> getFrequency(LocalDate firstDate, LocalDate secondDate) {
        Map<UUID, Integer> result = new HashMap<>();
        orderRepository.findAllPaidAndCompletedBetweenDates(firstDate.atStartOfDay(), secondDate.plusDays(1).atStartOfDay())
                .stream()
                .flatMap(order -> order.getPositions()
                        .stream())
                .forEach(position -> {
                    if (result.containsKey(position.getDishId())) {
                        result.put(position.getDishId(), result.get(position.getDishId()) + position.getQuantity());
                    } else {
                        result.put(position.getDishId(), position.getQuantity());
                    }
                });
        return result;
    }

    private Order getOrder(OrderRequestDto dto) {
        Order order;
        Optional<Order> optionalOrder = orderRepository.findByClientIdOrWaiterIdAndStatus(dto.getClientId(), dto.getWaiterId(), OrderStatus.DRAFT);
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
            removeQuartzTrigger(order);
        } else {
            order = generateOrder(dto);
        }
        order.setLastModified(LocalDateTime.now());
        order.setIsPaid(false);
        order.setAddress(dto.getAddress());
        order.setType(dto.getType());
        order.setAppliedBonuses(BigDecimal.ZERO);
        orderRepository.save(order);
        return order;
    }

    private void actAdditionalActionsDependingOnTheOrderStatus(ChangeOrderStatusDto dto, Order order) {
        if (!Objects.equals(dto.getOrderStatus(), order.getStatus())) {
            switch (dto.getOrderStatus()) {
                case CONFIRMED -> {
                    removeQuartzTrigger(order);
                    //создаем тикет на сервисе кухни
                    kitchenService.createTicket(order);
                }
                case CLOSED -> {
                    order.setEndTime(LocalDateTime.now());
                    //возвращаем ингридиенты блюд
                    restaurantService.accrualComponentsOfPositions(order);
                }
                case DONE -> {
                    order.setIsPaid(Boolean.TRUE);
                    order.setEndTime(LocalDateTime.now());
                }
            }
        }
    }

    private BigDecimal getBonusPercent() {
        return new BigDecimal(bonusService.getParameter(BonusServiceConfigParameters.BONUS_PERCENT.name()));
    }

    private void calculatePrice(Order order) {
        BigDecimal sum = order.getPositions()
                .stream()
                .map(position -> position.getCost().multiply(BigDecimal.valueOf(position.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setFullPrice(sum);
        order.setTotalPrice(sum);
    }

    private static Supplier<NotFoundException> getNotFoundExceptionDueToClientSupplier(OrderRequestDto dto) {
        return () -> new NotFoundException("Не найден актуальный заказ для клиента " + dto.getClientId());
    }

    private static Supplier<NotFoundException> getNotFoundExceptionDueToOrderSupplier(UUID uuid) {
        return () -> new NotFoundException("Не найден актуальный заказ для заказа " + uuid);
    }

    private Order generateOrder(OrderRequestDto dto) {
        Order order = new Order();
        order.setCreationTime(LocalDateTime.now());
        order.setPositions(new HashSet<>());
        order.setClientId(dto.getClientId());
        order.setWaiterId(dto.getWaiterId());
        order.setStatus(OrderStatus.DRAFT);
        return order;
    }

    private void removeQuartzTrigger(Order order) {
        //удаление тригера на удаление заказа
        try {
            schedulerService.unscheduleJob(order);
        } catch (SchedulerException e) {
            log.error("Ошибка при удалении тригера на отмену заказа " + order.getId());
        }
    }

    private void createQuartzTrigger(Order order) {
        //создание тригера на удаление заказа
        try {
            schedulerService.scheduleJob(order, CancelOrderJob.class);
        } catch (SchedulerException e) {
            log.error("Ошибка при создании тригера для заказа " + order.getId());
        }
    }
}
