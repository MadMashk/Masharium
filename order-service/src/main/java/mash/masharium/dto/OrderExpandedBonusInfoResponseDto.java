package mash.masharium.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderExpandedBonusInfoResponseDto extends OrderResponseDto {

    private Integer bonusesForOrder;
}
