package mash.masharium.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReportRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate firstDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate secondDate;
}
