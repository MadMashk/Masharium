package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.kitchen.TicketRequestDto;
import mash.masharium.entity.Order;
import mash.masharium.entity.Position;
import mash.masharium.integration.client.KitchenServiceClient;
import mash.masharium.service.KitchenService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {

    private final KitchenServiceClient kitchenServiceClient;

    @Override
    public void createTicket(Order order) {
        TicketRequestDto ticketRequestDto = new TicketRequestDto();
        ticketRequestDto.setOrderId(order.getId());
        ticketRequestDto.setDishIds(order.getPositions()
                .stream()
                .map(Position::getId)
                .toList());
        kitchenServiceClient.create(ticketRequestDto);
    }
}
