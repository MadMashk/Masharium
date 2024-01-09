package mash.masharium.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ReservationResponseDto {
    private UUID id;
    private UUID tableId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime time;
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime endTime;
    List<ReservationClientDto> tableClients;
    ReservationTableDto restaurantTable;
}
