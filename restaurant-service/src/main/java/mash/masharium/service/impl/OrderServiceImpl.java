package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.client.OrderServiceClient;
import mash.masharium.service.OrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderServiceClient orderServiceClient;

    @Override
    public Map<LocalDate, BigDecimal> getVolumesPerMonths(LocalDate firstDate, LocalDate secondDate) {
        return orderServiceClient.getVolumesPerMonths(firstDate, secondDate);
    }

    @Override
    public Map<UUID, Integer> getFrequency(LocalDate firstDate, LocalDate secondDate) {
        return orderServiceClient.getFrequency(firstDate, secondDate);
    }
}
