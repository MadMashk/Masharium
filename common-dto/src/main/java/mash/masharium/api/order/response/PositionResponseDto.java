package mash.masharium.api.order.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class PositionResponseDto {

    private UUID id;

    private Integer quantity;

    private BigDecimal cost;
}
