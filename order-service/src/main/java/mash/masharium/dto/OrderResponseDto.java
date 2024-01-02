package mash.masharium.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
public class OrderResponseDto {

    private UUID id;

    private UUID clientId;

    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    private LocalDateTime lastModified;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    private LocalDateTime creationTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm:ss")
    private LocalDateTime endTime;

    private Set<PositionDto> positions;

    private BigDecimal fullPrice;

    private BigDecimal appliedBonuses;

    private BigDecimal totalPrice;
}
