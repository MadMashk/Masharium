package mash.masharium.service;

import mash.masharium.api.bonus.request.CreateBonusRequest;
import mash.masharium.api.bonus.response.BonusAccountResponseDto;
import mash.masharium.api.bonus.response.OperationsHistoryDto;

import java.util.UUID;

public interface BonusAccountService {
    
    UUID create(UUID userId);

    void accrual(UUID userId, UUID orderId, Integer summa);

    BonusAccountResponseDto get(UUID userId);

    void writeOff(UUID userId, UUID orderId, Integer summa);

    OperationsHistoryDto getOperations(UUID userId);

}
