package mash.masharium.service;

import mash.masharium.api.order.request.PositionRequestDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.entity.Order;
import mash.masharium.entity.Position;

import java.util.List;
import java.util.Set;

public interface PositionService {

    List<PositionResponseDto> subtract(List<PositionRequestDto> positionRequestDtos, Order order);

    Position save(Position entity);

    Set<Position> calculateNewVolumeOfAlreadyExistPositionsAndGetNewPositions(List<PositionRequestDto> positionRequestDtos, Set<Position> positions, Order order);
}
