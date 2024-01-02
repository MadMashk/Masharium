package mash.masharium.service;

import mash.masharium.api.bonus.response.BonusAccountResponseDto;
import mash.masharium.entity.Order;

import java.util.UUID;

public interface BonusService {

    void accrual(Order order, Integer amount);

    void writeOff(Order order, Integer amount);

    BonusAccountResponseDto getAccount(UUID clientId);

    String getParameter(String parameterName);
}
