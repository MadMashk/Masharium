package mash.masharium.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PositionDto {

    private UUID id;

    private Integer quantity;

    private BigDecimal cost;
}
