package mash.masharium.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mash.masharium.dto.ReportRequestDto;
import mash.masharium.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;

@RestController
@Tag(name = "Отчеты")
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/frequency")
    public byte[] getFrequency(@RequestBody ReportRequestDto dto) {
        return reportService.getDishOrdersFrequency(dto.getFirstDate(), dto.getSecondDate());
    }

    @PostMapping("/sales")
    public byte[] getSales(@RequestBody ReportRequestDto dto) {
        return reportService.getSalesVolumes(dto.getFirstDate(), dto.getSecondDate());
    }

    @PostMapping("/expected-dishes")
    public byte[] getExpectedDishes(@RequestParam Integer quantityOfMonthsToAnalyze) {
        return reportService.getExpectedDishNumbers(quantityOfMonthsToAnalyze);
    }

    @PostMapping("/expected-products")
    public byte[] getExpectedProducts(@RequestParam Integer quantityOfMonthsToAnalyze) {
        return reportService.getExpectedProductVolumes(quantityOfMonthsToAnalyze);
    }
}
