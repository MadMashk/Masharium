package mash.masharium.api.order.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mash.masharium.api.order.constant.OrderStatus;
import mash.masharium.api.order.constant.OrderType;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestDto {

    private UUID clientId;

    private String address;

    private Boolean isAuth;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OrderStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OrderType type;

    private List<PositionRequestDto> positions;

}
