package mash.masharium.service;

import mash.masharium.api.order.request.PositionRequestDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.entity.Order;

import java.util.List;

public interface RestaurantService {

    void writeOffComponentsOfPositions(Order order, List<PositionRequestDto> positionRequestDtoList);

    void accrualComponentsOfPositions(Order order);

    void accrualComponentsOfPositions(Order order, List<PositionResponseDto> positionResponseDtoList);

}
