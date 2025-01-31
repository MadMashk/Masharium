package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mash.masharium.api.order.request.PositionRequestDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.api.restaurant.request.DishesComponentsQuantityChangingRequest;
import mash.masharium.entity.Order;
import mash.masharium.entity.Position;
import mash.masharium.integration.client.RestaurantServiceClient;
import mash.masharium.service.RestaurantService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantServiceClient restaurantServiceClient;

    @Override
    public void writeOffComponentsOfPositions(Order order, Map<UUID, Integer> quantities) {
        writeOffPositions(order.getId(), quantities);
    }

    @Override
    public void accrualComponentsOfPositions(Order order) {
        accrualPositions(order.getId(), getPositionsAsIdQuantityMap(order));
    }

    @Override
    public void accrualComponentsOfPositions(Order order, List<PositionResponseDto> positionResponseDtoList) {
        accrualPositions(order.getId(), positionResponseDtoList);
    }

    private static Map<UUID, Integer> getPositionsAsIdQuantityMap(Order order) {
        return order.getPositions().stream().collect(Collectors.toMap(Position::getId, Position::getQuantity));
    }

    private void writeOffPositions(UUID orderId, Map<UUID, Integer> quantities) {
        DishesComponentsQuantityChangingRequest request = new DishesComponentsQuantityChangingRequest();
        request.setOrderId(orderId);
        request.setDishesQuantityMap(quantities);
        sendWriteOffRequest(request);
    }

    private void accrualPositions(UUID orderId, List<PositionResponseDto> positionRequestDtos) {
        DishesComponentsQuantityChangingRequest request = new DishesComponentsQuantityChangingRequest();
        request.setOrderId(orderId);
        request.setDishesQuantityMap(positionRequestDtos
                .stream()
                .collect(Collectors.toMap(PositionResponseDto::getId, PositionResponseDto::getQuantity)));
        sendAccrualRequest(request);
    }

    private void accrualPositions(UUID orderId, Map<UUID, Integer> positions) {
        DishesComponentsQuantityChangingRequest request = new DishesComponentsQuantityChangingRequest();
        request.setOrderId(orderId);
        request.setDishesQuantityMap(positions);
        sendAccrualRequest(request);
    }

    private void sendWriteOffRequest(DishesComponentsQuantityChangingRequest request) {
        restaurantServiceClient.writeOffComponentsOfDishes(request);
    }

    private void sendAccrualRequest(DishesComponentsQuantityChangingRequest request) {
        restaurantServiceClient.accrualComponentsOfDishes(request);
    }
}
