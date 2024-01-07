package mash.masharium.service;

import java.time.LocalDate;

public interface ReportService {

    byte[] getSalesVolumes(LocalDate firstDate, LocalDate secondDate);

    byte[] getDishOrdersFrequency(LocalDate firstDate, LocalDate secondDate);

    byte[] getExpectedDishNumbers(Integer quantityOfMonthsForAnalyzeStatistic);

}
