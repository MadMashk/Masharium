package mash.masharium.api.kitchen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDishRequestDto {
    private UUID dishId;
    private Integer quantity;
}
