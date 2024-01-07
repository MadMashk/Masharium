package mash.masharium.api.kitchen;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class TicketRequestDto {

    private UUID orderId;

    private List<UUID> dishIds;
}
