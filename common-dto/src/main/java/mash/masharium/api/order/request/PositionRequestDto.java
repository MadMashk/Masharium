package mash.masharium.api.order.request;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PositionRequestDto {

    private UUID id;

    private Integer quantity;

    private BigDecimal cost;
}
