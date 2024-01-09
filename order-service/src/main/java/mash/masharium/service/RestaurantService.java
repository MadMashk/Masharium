package mash.masharium.service;

import mash.masharium.api.order.request.PositionRequestDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.entity.Order;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface RestaurantService {

    void writeOffComponentsOfPositions(Order order, Map<UUID, Integer> quantities);

    void accrualComponentsOfPositions(Order order);

    void accrualComponentsOfPositions(Order order, List<PositionResponseDto> positionResponseDtoList);

}
