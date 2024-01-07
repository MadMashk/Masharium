package mash.masharium.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public interface OrderService {

    Map<LocalDate, BigDecimal> getVolumesPerMonths(LocalDate firstDate, LocalDate secondDate);

    Map<UUID, Integer> getFrequency(LocalDate firstDate, LocalDate secondDate);
}
