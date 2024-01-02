package mash.masharium.integration.client;

import mash.masharium.api.bonus.response.BonusAccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(value = "parameters", url = "#{clientProperties.bonusServiceUrl}")
public interface BonusServiceParametersClient {

    @GetMapping("/name")
    String getParameter(@RequestParam String parameterName);

}
