package mash.masharium.api.order.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import mash.masharium.api.order.constant.OrderStatus;
import mash.masharium.api.order.constant.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderResponseDto {

    private UUID id;

    private UUID clientId;

    private Boolean isAuth;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OrderStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OrderType type;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    private LocalDateTime lastModified;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    private LocalDateTime creationTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    private LocalDateTime endTime;

    private String address;

    private Set<PositionResponseDto> positions;

    private Boolean isPaid;

    private BigDecimal fullPrice;

    private BigDecimal totalPrice;

    private BigDecimal appliedBonuses;
}
