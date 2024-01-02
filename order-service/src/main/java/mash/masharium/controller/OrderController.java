package mash.masharium.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mash.masharium.api.order.request.ChangeOrderStatusDto;
import mash.masharium.api.order.request.OrderRequestDto;
import mash.masharium.api.order.response.OrderResponseDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.dto.OrderExpandedBonusInfoResponseDto;
import mash.masharium.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Заказы")
@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Добавление позиций", tags = "Заказы")
    @PostMapping("/adding")
    public void createOrUpdate(@RequestBody OrderRequestDto dto) {
        orderService.createOrUpdate(dto);
    }

    @Operation(summary = "Удаление позиций", tags = "Заказы")
    @PostMapping("/removing")
    public List<PositionResponseDto> remove(@RequestBody OrderRequestDto dto) {
        return orderService.remove(dto);
    }

    @Operation(summary = "Получение списка заказов клиента", tags = "Заказы")
    @GetMapping
    public List<OrderResponseDto> getAllByClient(@RequestParam UUID clientId) {
        return orderService.getAllByClient(clientId);
    }

    @Operation(summary = "Смена статуса заказа клиента", tags = "Заказы")
    @PostMapping
    public void changeStatus(@RequestBody ChangeOrderStatusDto dto) {
        orderService.changeStatus(dto);
    }

    @Operation(summary = "Оплата заказа", tags = "Заказы")
    @PostMapping("/payment")
    public void pay(@RequestParam UUID orderId) {
        orderService.pay(orderId);
    }

    @Operation(summary = "Информация о заказе перед оплатой", tags = "Заказы")
    @GetMapping("/info")
    public OrderExpandedBonusInfoResponseDto getInfo(@RequestParam UUID orderId) {
        return orderService.getPaymentInfo(orderId);
    }

    @Operation(summary = "Списать бонусы на заказ", tags = "Заказы")
    @PatchMapping("/bonuses")
    public void spendBonuses(@RequestParam UUID orderId, Integer amount) {
        orderService.spendBonuses(orderId, amount);
    }

    @Operation(summary = "Получение всех активных заказов", tags = "Заказы")
    @GetMapping("/all-active")
    public List<OrderResponseDto> getAllActive() {
        return orderService.getAllActive();
    }
}
