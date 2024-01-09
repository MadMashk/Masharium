package mash.masharium.integration.client;

import mash.masharium.api.bonus.response.BonusAccountResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(value = "bonus-accounts", url = "#{clientProperties.bonusServiceUrl}")
public interface BonusServiceClient {

    @PostMapping("/bonus-accounts/writing-off")
    void writeOff(@RequestParam UUID userId, @RequestParam UUID orderId, @RequestParam Integer summa);

    @PostMapping("/bonus-accounts/accrual")
    void accrual(@RequestParam UUID userId, @RequestParam UUID orderId, @RequestParam Integer summa);

    @GetMapping("/bonus-accounts")
    BonusAccountResponseDto getAccount(@RequestParam UUID userId);

}
