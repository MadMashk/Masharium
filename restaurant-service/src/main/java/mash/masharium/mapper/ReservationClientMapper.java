package mash.masharium.mapper;

import mash.masharium.dto.ReservationClientDto;
import mash.masharium.entity.TableClient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationClientMapper {
    ReservationClientDto toDto(TableClient tableClient);

    List<ReservationClientDto> toListDto(List<TableClient> tableClients);
}
