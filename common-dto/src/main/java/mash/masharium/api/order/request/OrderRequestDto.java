package mash.masharium.api.order.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import mash.masharium.api.order.constant.OrderStatus;

import java.util.List;
import java.util.UUID;

@Data
public class OrderRequestDto {

    @NotNull
    private UUID clientId;

    private String address;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OrderStatus status;

    private List<PositionRequestDto> positions;

}
