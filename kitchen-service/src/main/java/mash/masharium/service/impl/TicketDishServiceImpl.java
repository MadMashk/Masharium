package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.entity.Ticket;
import mash.masharium.entity.TicketDish;
import mash.masharium.repository.TicketDishRepository;
import mash.masharium.service.TicketDishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketDishServiceImpl implements TicketDishService {

    private final TicketDishRepository ticketDishRepository;

    @Override
    @Transactional
    public void saveAll(List<UUID> dishIds, Ticket ticket) {
        ticketDishRepository.saveAll(dishIds
                .stream()
                .map(id -> createTicketDish(id, ticket))
                .toList());
    }

    private TicketDish createTicketDish(UUID id, Ticket ticket) {
        TicketDish ticketDish = new TicketDish();
        ticketDish.setTicketId(ticket.getId());
        ticketDish.setDishId(id);
        return ticketDish;
    }
}
