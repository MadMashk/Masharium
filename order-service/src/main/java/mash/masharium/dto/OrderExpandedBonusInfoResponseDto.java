package mash.masharium.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import mash.masharium.api.order.response.OrderResponseDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderExpandedBonusInfoResponseDto extends OrderResponseDto {

    private Integer bonusesForOrder;
}
