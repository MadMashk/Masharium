package mash.masharium.integration.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(value = "bonus-accounts", url = "#{clientProperties.bonusServiceUrl}")
public interface BonusServiceClient {

    @PostMapping
    UUID create(@RequestParam String userId);

}
