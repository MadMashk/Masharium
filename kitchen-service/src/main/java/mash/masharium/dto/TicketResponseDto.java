package mash.masharium.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class TicketResponseDto {

    private UUID id;

    private UUID orderId;

    private UUID userExecutorId;

    private TicketStatus ticketStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    private LocalDateTime beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    private LocalDateTime endTime;

    List<TicketDishResponseDto> positions;
}
