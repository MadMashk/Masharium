package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.entity.Dish;
import mash.masharium.service.DishService;
import mash.masharium.service.OrderService;
import mash.masharium.service.ReportService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderService orderService;

    private final DishService dishService;

    @Override
    public byte[] getSalesVolumes(LocalDate firstDate, LocalDate secondDate) {
        Map<LocalDate, BigDecimal> monthVolumes = orderService.getVolumesPerMonths(firstDate, secondDate);
        BigDecimal generalVolume = calculateGeneralVolume(monthVolumes);
        return generateReport(generalVolume, monthVolumes);
    }

    @Override
    public byte[] getDishOrdersFrequency(LocalDate firstDate, LocalDate secondDate) {
        Map<UUID, Integer> frequency = orderService.getFrequency(firstDate, secondDate);
        List<Dish> dishes = dishService.getAll();
        Integer generalAmount = frequency.values().stream().mapToInt(Integer::intValue).sum();
        return generateFrequencyReport(dishes, frequency, generalAmount);
    }

    @Override
    public byte[] getExpectedDishNumbers(Integer quantityOfMonthsForAnalyzeStatistic) {
        return new byte[0];
    }

    private byte[] generateFrequencyReport(List<Dish> dishes, Map<UUID, Integer> frequency, Integer generalAmount) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Статистика продаж каждого блюда");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Блюдо");
        headerRow.createCell(1).setCellValue("Продано в шт");
        headerRow.createCell(2).setCellValue("% от общих продаж");
        AtomicInteger iterator = new AtomicInteger(1);

        dishes.forEach(dish -> putFrequencyData(dish, frequency, generalAmount, sheet.createRow(iterator.getAndIncrement())));
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void putFrequencyData(Dish dish, Map<UUID, Integer> frequency, Integer generalAmount, Row row) {
        Integer amount = frequency.getOrDefault(dish.getId(), 0);
        row.createCell(0).setCellValue(dish.getName());
        row.createCell(1).setCellValue(amount);
        row.createCell(2).setCellValue(BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(generalAmount), 3, RoundingMode.HALF_UP).toString());
    }

    private byte[] generateReport(BigDecimal generalVolume, Map<LocalDate, BigDecimal> volumes) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчет по объемам продаж");
        Row generalRow = sheet.createRow(0);
        generalRow.createCell(0).setCellValue("Общий объем продаж");
        generalRow.createCell(0).setCellValue(generalVolume.toString());
        Row headerRow = sheet.createRow(1);
        headerRow.createCell(0).setCellValue("Продажи по месяцам");

        AtomicInteger iterator = new AtomicInteger(2);

        volumes.entrySet().forEach(entry -> putData(entry, sheet.createRow(iterator.getAndIncrement())));
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void putData(Map.Entry<LocalDate, BigDecimal> localDateBigDecimalEntry, Row row) {
        row.createCell(0).setCellValue(localDateBigDecimalEntry.getKey().toString());
        row.createCell(1).setCellValue(localDateBigDecimalEntry.getValue().toString());
    }

    private BigDecimal calculateGeneralVolume(Map<LocalDate, BigDecimal> volumes) {
        return volumes.values()
                .stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
