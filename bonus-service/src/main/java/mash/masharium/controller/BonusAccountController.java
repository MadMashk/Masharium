package mash.masharium.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import mash.masharium.api.bonus.response.BonusAccountResponseDto;
import mash.masharium.api.bonus.response.OperationsHistoryDto;
import mash.masharium.service.BonusAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Бонусные счета")
@RestController
@RequestMapping("/bonus-accounts")
@RequiredArgsConstructor
public class BonusAccountController {

    private final BonusAccountService bonusAccountService;

    @PostMapping
    @Operation(description = "Создание бонусного счета по айди юзера", hidden = true)
    public UUID create(@RequestParam UUID userId) {
        return bonusAccountService.create(userId);
    }

    @GetMapping
    @Operation(description = "Получение бонусного счета по айди юзера")
    public BonusAccountResponseDto get(@RequestParam UUID userId) {
        return bonusAccountService.get(userId);
    }

    @PostMapping("/accrual")
    @Operation(description = "Зачисление бонусов на счет")
    public void accrual(@RequestParam UUID userId, @RequestParam UUID orderId, @RequestParam Integer summa) {
        bonusAccountService.accrual(userId, orderId, summa);
    }

    @PostMapping("/writing-off")
    @Operation(description = "Списание бонусов со счета", hidden = true)
    public void writeOff(@RequestParam UUID userId, @RequestParam UUID orderId, @RequestParam Integer summa) {
        bonusAccountService.writeOff(userId, orderId, summa);
    }

    @Operation(description = "Получение всех операций по счету")
    @GetMapping("/operations")
    public OperationsHistoryDto getOperations(@RequestParam UUID userId) {
        return bonusAccountService.getOperations(userId);
    }
}
