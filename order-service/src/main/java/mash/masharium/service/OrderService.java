package mash.masharium.service;

import mash.masharium.api.order.request.ChangeOrderStatusDto;
import mash.masharium.api.order.request.OrderRequestDto;
import mash.masharium.api.order.response.OrderResponseDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.dto.OrderExpandedBonusInfoResponseDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {

    void createOrUpdate(OrderRequestDto dto);

    List<PositionResponseDto> remove(OrderRequestDto dto);

    List<OrderResponseDto> getAllByClient(UUID clientId);

    void changeStatus(ChangeOrderStatusDto dto);

    void closeOrder(UUID orderId);

    void pay(UUID orderId);

    OrderExpandedBonusInfoResponseDto getPaymentInfo(UUID orderId);

    void spendBonuses(UUID orderId, Integer amount);

    List<OrderResponseDto> getAllActive();

    Map<LocalDate, BigDecimal> getVolumes(LocalDate firstDate, LocalDate secondDate);

    Map<UUID, Integer> getFrequency(LocalDate firstDate, LocalDate secondDate);
}
