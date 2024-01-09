package mash.masharium.service;

import mash.masharium.api.kitchen.TicketDishRequestDto;
import mash.masharium.entity.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketDishService {
    void saveAll(List<TicketDishRequestDto> dishIds, Ticket ticket);
}
