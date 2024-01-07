package mash.masharium.api.order.request;

import lombok.Data;
import mash.masharium.api.order.constant.OrderStatus;

import java.util.UUID;

@Data
public class ChangeOrderStatusDto {

    private UUID orderId;

    private OrderStatus orderStatus;

}
