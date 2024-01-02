package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mash.masharium.api.order.request.PositionRequestDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.api.restaurant.request.DishesComponentsWritingOffRequest;
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
    public void writeOffComponentsOfPositions(Order order, List<PositionRequestDto> positionRequestDtoList) {
        writeOffPositions(order.getId(), positionRequestDtoList);
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

    private void writeOffPositions(UUID orderId, List<PositionRequestDto> positionRequestDtos) {
        DishesComponentsWritingOffRequest request = new DishesComponentsWritingOffRequest();
        request.setOrderId(orderId);
        request.setDishesQuantityMap(positionRequestDtos
                .stream()
                .collect(Collectors.toMap(PositionRequestDto::getId, PositionRequestDto::getQuantity)));
        sendRequest(request);
    }

    private void accrualPositions(UUID orderId, List<PositionResponseDto> positionRequestDtos) {
        DishesComponentsWritingOffRequest request = new DishesComponentsWritingOffRequest();
        request.setOrderId(orderId);
        request.setDishesQuantityMap(positionRequestDtos
                .stream()
                .collect(Collectors.toMap(PositionResponseDto::getId, PositionResponseDto::getQuantity)));
        sendRequest(request);
    }

    private void accrualPositions(UUID orderId, Map<UUID, Integer> positions) {
        DishesComponentsWritingOffRequest request = new DishesComponentsWritingOffRequest();
        request.setOrderId(orderId);
        request.setDishesQuantityMap(positions);
        sendRequest(request);
    }

    private void sendRequest(DishesComponentsWritingOffRequest request) {
        restaurantServiceClient.accrualComponentsOfDishes(request);
    }
}
