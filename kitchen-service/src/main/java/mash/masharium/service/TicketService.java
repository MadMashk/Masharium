package mash.masharium.service;

import mash.masharium.api.kitchen.TicketRequestDto;
import mash.masharium.dto.TicketResponseDto;

import java.util.List;
import java.util.UUID;

public interface TicketService {
    void create(TicketRequestDto dto);

    List<TicketResponseDto> getAll();

    void take(UUID ticketId, String token);

    void complete(UUID ticketId);

    List<TicketResponseDto> getAllActive();
}
