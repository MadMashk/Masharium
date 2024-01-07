package mash.masharium.mapper;

import mash.masharium.dto.TicketResponseDto;
import mash.masharium.entity.Ticket;
import mash.masharium.entity.TicketDish;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = TicketDish.class)
public interface TicketMapper {

    TicketResponseDto toDto(Ticket ticket);

    List<TicketResponseDto> toListDto(List<Ticket> tickets);

}
