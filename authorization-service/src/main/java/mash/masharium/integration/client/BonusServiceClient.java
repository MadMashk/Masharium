package mash.masharium.integration.client;

import mash.masharium.api.bonus.request.CreateBonusRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "bonus-client", url = "#{clientProperties.bonusServiceUrl}")
public interface BonusServiceClient {

    @PostMapping("/bonus-account")
    ResponseEntity<UUID> createBonusAccount(@RequestBody CreateBonusRequest createBonusRequest);

}
