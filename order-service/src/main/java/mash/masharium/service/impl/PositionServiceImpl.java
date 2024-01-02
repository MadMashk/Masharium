package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.order.request.PositionRequestDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.entity.Order;
import mash.masharium.entity.Position;
import mash.masharium.repository.PositionRepository;
import mash.masharium.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public List<PositionResponseDto> subtract(List<PositionRequestDto> positionRequestDtos, Order order) {
        Set<Position> positions = order.getPositions();
        return positionRequestDtos
                .stream()
                .map(positionDto -> subtractRequest(positions
                        .stream()
                        .filter(positionEntity -> positionEntity.getId().equals(positionDto.getId()))
                        .findFirst().get(), positionDto))
                .toList();
    }

    @Override
    public Position save(Position entity) {
        return positionRepository.save(entity);
    }

    @Override
    public Set<Position> calculateNewVolumeOfAlreadyExistPositionsAndGetNewPositions(List<PositionRequestDto> positionRequestDtos, Set<Position> positions, Order order) {
        return new HashSet<>(positionRepository.saveAll(positionRequestDtos
                .stream()
                .peek(position -> {
                    Optional<Position> foundPosition = positions
                            .stream()
                            .filter(orderPosition -> orderPosition.getId().equals(position.getId()))
                            .findFirst();
                    foundPosition.ifPresent(value -> position.setQuantity(position.getQuantity() + value.getQuantity()));
                })
                .map(position -> {
                    Position entity = new Position();
                    entity.setId(position.getId());
                    entity.setOrderId(order.getId());
                    entity.setCost(position.getCost());
                    entity.setQuantity(position.getQuantity());
                    return entity;
                }).collect(Collectors.toSet())));
    }

    private PositionResponseDto subtractRequest(Position position, PositionRequestDto dto) {
        if (Objects.isNull(position)) {
            return new PositionResponseDto(dto.getId(), 0, BigDecimal.ZERO);
        }
        if (position.getQuantity() - dto.getQuantity() > 0) {
            position.setQuantity(position.getQuantity() - dto.getQuantity());
            positionRepository.save(position);
            return generateRemovedPositionResponseDto(dto);
        }
        if (position.getQuantity() - dto.getQuantity() == 0) {
            positionRepository.delete(position);
            return generateRemovedPositionResponseDto(dto);
        }
        dto.setQuantity(position.getQuantity());
        positionRepository.delete(position);
        return generateRemovedPositionResponseDto(dto);
    }

    private PositionResponseDto generateRemovedPositionResponseDto(PositionRequestDto dto) {
        return new PositionResponseDto(dto.getId(), dto.getQuantity(), dto.getCost());
    }
}
