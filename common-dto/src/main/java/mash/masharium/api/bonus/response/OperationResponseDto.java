package mash.masharium.api.bonus.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd dd-HH-mm")
    LocalDateTime time;

    OperationStatus status;

    OperationType type;

    UUID orderId;
}
