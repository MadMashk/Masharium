package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mash.masharium.api.order.request.PositionRequestDto;
import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.entity.Order;
import mash.masharium.entity.Position;
import mash.masharium.exception.model.NotFoundException;
import mash.masharium.repository.PositionRepository;
import mash.masharium.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    @Transactional
    public List<PositionResponseDto> subtract(List<PositionRequestDto> positionRequestDtos, Order order) {
        Set<Position> positions = order.getPositions();
        try {
            return positionRequestDtos
                    .stream()
                    .map(positionDto -> subtractRequest(positions
                            .stream()
                            .filter(positionEntity -> positionEntity.getDishId().equals(positionDto.getId()))
                            .findFirst().get(), positionDto))
                    .toList();
        } catch (NoSuchElementException e) {
            throw new NotFoundException("В заказе " + order.getId() + " отсутствуют указанные позиции");
        }
    }

    @Override
    public Position save(Position entity) {
        return positionRepository.save(entity);
    }

    @Override
    public Set<Position> calculateNewVolumeOfAlreadyExistPositionsAndGetNewPositions(List<PositionRequestDto> positionRequestDtos, Order order) {
        Set<Position> positions = order.getPositions();
        return new HashSet<>(positionRepository.saveAll(positionRequestDtos
                .stream()
                .peek(positionDto -> {
                    Optional<Position> foundPosition = positions
                            .stream()
                            .filter(position -> position.getDishId().equals(positionDto.getId()))
                            .findFirst();
                    foundPosition.ifPresent(value -> value.setQuantity(positionDto.getQuantity() + value.getQuantity()));
                })
                .map(dto -> {
                    Optional<Position> foundPosition = positions
                            .stream()
                            .filter(position -> position.getDishId().equals(dto.getId()))
                            .findFirst();
                    if (foundPosition.isPresent()) {
                        return foundPosition.get();
                    } else {
                        Position entity = new Position();
                        entity.setDishId(dto.getId());
                        entity.setOrderId(order.getId());
                        entity.setCost(dto.getCost());
                        entity.setQuantity(dto.getQuantity());
                        return entity;
                    }
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
