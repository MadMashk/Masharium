package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.kitchen.TicketDishRequestDto;
import mash.masharium.entity.Ticket;
import mash.masharium.entity.TicketDish;
import mash.masharium.repository.TicketDishRepository;
import mash.masharium.service.TicketDishService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketDishServiceImpl implements TicketDishService {

    private final TicketDishRepository ticketDishRepository;

    @Override
    @Transactional
    public void saveAll(List<TicketDishRequestDto> dishes, Ticket ticket) {
        ticketDishRepository.saveAll(dishes
                .stream()
                .map(dto -> createTicketDish(dto, ticket))
                .toList());
    }

    private TicketDish createTicketDish(TicketDishRequestDto dto, Ticket ticket) {
        TicketDish ticketDish = new TicketDish();
        ticketDish.setTicketId(ticket.getId());
        ticketDish.setDishId(dto.getDishId());
        ticketDish.setQuantity(dto.getQuantity());
        return ticketDish;
    }
}
