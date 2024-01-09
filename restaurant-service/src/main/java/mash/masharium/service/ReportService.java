package mash.masharium.service;

import java.io.FileOutputStream;
import java.time.LocalDate;

public interface ReportService {

    byte[] getSalesVolumes(LocalDate firstDate, LocalDate secondDate);

    byte[] getDishOrdersFrequency(LocalDate firstDate, LocalDate secondDate);

    byte[] getExpectedDishNumbers(Integer quantityOfMonthsForAnalyzeStatistic);

    byte[] getExpectedProductVolumes(Integer quantityOfMonthsToAnalyze);
}
