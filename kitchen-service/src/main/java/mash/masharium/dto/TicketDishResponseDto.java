package mash.masharium.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class TicketDishResponseDto {

    private UUID id;

    private UUID ticketId;

    private UUID dishId;

}
