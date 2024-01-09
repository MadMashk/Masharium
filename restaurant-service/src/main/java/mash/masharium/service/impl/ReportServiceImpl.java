package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.entity.Component;
import mash.masharium.entity.Dish;
import mash.masharium.service.ComponentService;
import mash.masharium.service.DishService;
import mash.masharium.service.OrderService;
import mash.masharium.service.ReportService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final OrderService orderService;

    private final DishService dishService;

    private final ComponentService componentService;

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
        Integer generalAmount = frequency.values()
                .stream()
                .mapToInt(Integer::intValue)
                .sum();
        return generateFrequencyReport(dishes, frequency, generalAmount);
    }

    @Override
    public byte[] getExpectedDishNumbers(Integer quantityOfMonthsForAnalyzeStatistic) {
        LocalDate firstDate = LocalDate.now().minusDays(1).minusMonths(quantityOfMonthsForAnalyzeStatistic);
        LocalDate secondDate = LocalDate.now().minusDays(1);
        Long quantityOfDays = ChronoUnit.DAYS.between(firstDate, secondDate);
        List<Dish> dishes = dishService.getAll();
        Map<UUID, Integer> frequencyOfDishes = orderService.getFrequency(firstDate, secondDate);
        return generateExpectedDishNumbersReport(dishes, frequencyOfDishes, quantityOfDays);
    }

    @Override
    public byte[] getExpectedProductVolumes(Integer quantityOfMonthsForAnalyzeStatistic) {
        LocalDate firstDate = LocalDate.now().minusDays(1).minusMonths(quantityOfMonthsForAnalyzeStatistic);
        LocalDate secondDate = LocalDate.now().minusDays(1);
        Long quantityOfDays = ChronoUnit.DAYS.between(firstDate, secondDate);
        List<Dish> dishes = dishService.getAll();
        Map<UUID, Dish> uuidDishMap = dishes.stream().collect(Collectors.toMap(Dish::getId, dish -> dish));
        Map<UUID, Integer> frequencyOfDishes = orderService.getFrequency(firstDate, secondDate);
        Map<UUID, Integer> frequencyOfProducts = new HashMap<>();
        List<Component> components = componentService.getAll();
        frequencyOfDishes.keySet().forEach(key -> {
            Dish dish = uuidDishMap.get(key);
            dish.getDishComponents().forEach(component -> {
                if (frequencyOfProducts.containsKey(component.getComponent().getId())) {
                    frequencyOfProducts.put(component.getComponent().getId(), frequencyOfProducts.get(component.getComponent().getId()) + component.getQuantity().intValue() * frequencyOfDishes.get(key));
                } else {
                    frequencyOfProducts.put(component.getComponent().getId(), component.getQuantity().intValue() * frequencyOfDishes.get(key));
                }
            });
        });
        return generateExpectedComponentNumbersReport(components, frequencyOfProducts, quantityOfDays);
    }

    private byte[] generateExpectedComponentNumbersReport(List<Component> components, Map<UUID, Integer> frequencyOfProducts, Long quantityOfDays) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Статистика ежедневных трат каждого продукта");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Продукт");
        headerRow.createCell(1).setCellValue("Продано в кг");
        headerRow.createCell(2).setCellValue("Ожидаемое потребление за день");
        AtomicInteger iterator = new AtomicInteger(1);

        components.forEach(component -> putExpectedComponentData(component, frequencyOfProducts, quantityOfDays, sheet.createRow(iterator.getAndIncrement())));
        try {
            FileOutputStream outputStream = new FileOutputStream("resources.xlsx");
            workbook.write(outputStream);
           } catch (IOException e) {
            throw new RuntimeException(e);
        }try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] generateExpectedDishNumbersReport(List<Dish> dishes, Map<UUID, Integer> frequency, Long quantityOfDays) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Статистика продаж каждого блюда");
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Блюдо");
        headerRow.createCell(1).setCellValue("Продано в шт");
        headerRow.createCell(2).setCellValue("Ожидаемые продажи за день");
        AtomicInteger iterator = new AtomicInteger(1);

        dishes.forEach(dish -> putExpectedData(dish, frequency, quantityOfDays, sheet.createRow(iterator.getAndIncrement())));
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        row.createCell(2).setCellValue(BigDecimal.valueOf(amount).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(generalAmount), 3, RoundingMode.HALF_UP).toString());
    }

    private void putExpectedData(Dish dish, Map<UUID, Integer> frequency, Long quantityOfDays, Row row) {
        Integer amount = frequency.getOrDefault(dish.getId(), 0);
        row.createCell(0).setCellValue(dish.getName());
        row.createCell(1).setCellValue(amount);
        row.createCell(2).setCellValue(BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(quantityOfDays), 3, RoundingMode.HALF_UP).toString());
    }

    private void putExpectedComponentData(Component component, Map<UUID, Integer> frequency, Long quantityOfDays, Row row) {
        Integer amount = frequency.getOrDefault(component.getId(), 0);
        row.createCell(0).setCellValue(component.getName());
        row.createCell(1).setCellValue(amount);
        row.createCell(2).setCellValue(BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(quantityOfDays), 3, RoundingMode.HALF_UP).toString());
    }

    private byte[] generateReport(BigDecimal generalVolume, Map<LocalDate, BigDecimal> volumes) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Отчет по объемам продаж");
        Row generalRow = sheet.createRow(0);
        generalRow.createCell(0).setCellValue("Общий объем продаж");
        generalRow.createCell(1).setCellValue(generalVolume.toString());
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
