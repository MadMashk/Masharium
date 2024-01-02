package mash.masharium.mapper;

import mash.masharium.api.order.response.PositionResponseDto;
import mash.masharium.entity.Position;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface PositionMapper {

    PositionResponseDto toDto(Position position);

    Set<PositionResponseDto> toSetDto(Set<Position> positions);

}
