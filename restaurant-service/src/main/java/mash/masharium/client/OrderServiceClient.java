package mash.masharium.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@FeignClient(value = "order-client", url = "#{clientProperties.orderServiceUrl}")
public interface OrderServiceClient {

    @GetMapping("/orders/volumes")
    Map<LocalDate, BigDecimal> getVolumesPerMonths(@RequestParam LocalDate firstDate, @RequestParam LocalDate secondDate);

    @GetMapping("/orders/frequency")
    Map<UUID, Integer> getFrequency(LocalDate firstDate, LocalDate secondDate);
}
