package mash.masharium.api.bonus.response;

import lombok.Data;
import mash.masharium.api.bonus.constant.OperationStatus;
import mash.masharium.api.bonus.constant.OperationType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class OperationResponseDto {

    UUID id;

    UUID accountId;

    Integer summa;

    LocalDateTime time;

    OperationStatus status;

    OperationType type;

    UUID orderId;
}
