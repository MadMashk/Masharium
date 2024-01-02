package mash.masharium.mapper;

import mash.masharium.api.order.response.OrderResponseDto;
import mash.masharium.dto.OrderExpandedBonusInfoResponseDto;
import mash.masharium.entity.Order;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = PositionMapper.class)
public interface OrderMapper {

    OrderExpandedBonusInfoResponseDto toDto(Order order);

    List<OrderResponseDto> toListDto(List<Order> orders);

}
