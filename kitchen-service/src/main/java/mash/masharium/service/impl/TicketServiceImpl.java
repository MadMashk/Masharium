package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.kitchen.TicketRequestDto;
import mash.masharium.api.order.constant.OrderStatus;
import mash.masharium.api.order.request.ChangeOrderStatusDto;
import mash.masharium.api.order.request.OrderRequestDto;
import mash.masharium.dto.TicketResponseDto;
import mash.masharium.dto.TicketStatus;
import mash.masharium.entity.Ticket;
import mash.masharium.exception.model.NotFoundException;
import mash.masharium.exception.model.ValidationException;
import mash.masharium.integration.client.AuthServiceClient;
import mash.masharium.integration.client.OrderServiceClient;
import mash.masharium.mapper.TicketMapper;
import mash.masharium.repository.TicketRepository;
import mash.masharium.service.TicketDishService;
import mash.masharium.service.TicketService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final TicketDishService ticketDishService;

    private final TicketMapper ticketMapper;

    private final AuthServiceClient authServiceClient;

    private final OrderServiceClient orderServiceClient;

    @Override
    @Transactional
    public void create(TicketRequestDto dto) {
        Optional<Ticket> optional = ticketRepository.findByOrderId(dto.getOrderId());
        if (optional.isPresent()) {
            throw new ValidationException("Тикет с заказом " + dto.getOrderId() + " уже существует");
        }
        Ticket ticket = new Ticket();
        ticket.setTicketStatus(TicketStatus.NEW);
        ticket.setOrderId(dto.getOrderId());
        ticketRepository.save(ticket);
        ticketDishService.saveAll(dto.getDishes(), ticket);
    }

    @Override
    @Transactional
    public List<TicketResponseDto> getAll() {
        return ticketMapper.toListDto(ticketRepository.findAll());
    }

    @Override
    @Transactional
    public void take(UUID ticketId, String token) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(
                () -> new NotFoundException("Тикет с айди " + ticketId + " не найден")
        );
        ticket.setBeginTime(LocalDateTime.now());
        ticket.setTicketStatus(TicketStatus.IN_PROGRESS);
        SecurityContextHolder.getContext().getAuthentication().getName();
        ticket.setUserExecutorId(getUserId(token));
        changeOrderStatus(ticket, OrderStatus.IN_PROGRESS);
    }

    @Override
    @Transactional
    public void complete(UUID ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        ticket.setEndTime(LocalDateTime.now());
        ticket.setTicketStatus(TicketStatus.DONE);
        //отправиться на сервис заказов чтоб поменять статус там
        changeOrderStatus(ticket, OrderStatus.READY_FOR_DELIVERY);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TicketResponseDto> getAllActive() {
        return ticketMapper.toListDto(ticketRepository.findAllActive());
    }

    private void changeOrderStatus(Ticket ticket, OrderStatus inProgress) {
        //отправиться на сервис заказов чтоб поменять статус там
        ChangeOrderStatusDto changeOrderStatusDto = new ChangeOrderStatusDto();
        changeOrderStatusDto.setOrderId(ticket.getOrderId());
        changeOrderStatusDto.setOrderStatus(inProgress);
        orderServiceClient.changeStatus(changeOrderStatusDto);
    }

    private UUID getUserId(String token) {
        return authServiceClient.getUserInfo(token).getUserId();
    }
}
