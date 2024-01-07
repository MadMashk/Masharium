package mash.masharium.service;

import mash.masharium.entity.Ticket;

import java.util.List;
import java.util.UUID;

public interface TicketDishService {
    void saveAll(List<UUID> dishIds, Ticket ticket);
}
