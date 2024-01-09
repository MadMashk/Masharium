package mash.masharium.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Validated
public class ReservationRequestDto {
    private UUID id;
    private UUID tableId;
    private UUID clientId;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime time;
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime endTime;

    @AssertTrue
    boolean isValid() {
        return endTime.isAfter(time);
    }
}
