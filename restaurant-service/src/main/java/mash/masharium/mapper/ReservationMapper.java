package mash.masharium.mapper;

import mash.masharium.dto.ReservationResponseDto;
import mash.masharium.entity.TableReservation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ReservationClientMapper.class, ReservationTableMapper.class})
public interface ReservationMapper {

    ReservationResponseDto toDto(TableReservation tableReservation);

    List<ReservationResponseDto> toListDto(List<TableReservation> tableReservations);
}
