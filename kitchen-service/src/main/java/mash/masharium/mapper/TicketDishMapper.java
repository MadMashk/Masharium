package mash.masharium.mapper;

import mash.masharium.dto.TicketDishResponseDto;
import mash.masharium.entity.TicketDish;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketDishMapper {

    TicketDishResponseDto toDto(TicketDish ticketDish);

    List<TicketDishResponseDto> toListDto(List<TicketDish> ticketDishes);
}
