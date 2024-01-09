package mash.masharium.mapper;

import mash.masharium.dto.ReservationClientDto;
import mash.masharium.dto.ReservationTableDto;
import mash.masharium.entity.RestaurantTable;
import mash.masharium.entity.TableClient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationTableMapper {
    ReservationTableDto toDto(RestaurantTable restaurantTable);

    List<ReservationTableDto> toListDto(List<RestaurantTable> restaurantTables);
}
