package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.bonus.response.BonusAccountResponseDto;
import mash.masharium.entity.Order;
import mash.masharium.integration.client.BonusServiceClient;
import mash.masharium.integration.client.BonusServiceParametersClient;
import mash.masharium.service.BonusService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BonusServiceImpl implements BonusService {

    private final BonusServiceClient bonusServiceClient;

    private final BonusServiceParametersClient bonusServiceParametersClient;

    @Override
    public void accrual(Order order, Integer amount) {
        bonusServiceClient.accrual(order.getClientId(), order.getId(), amount);
    }

    @Override
    public void writeOff(Order order, Integer amount) {
        bonusServiceClient.writeOff(order.getClientId(), order.getId(), amount);
    }

    @Override
    public BonusAccountResponseDto getAccount(UUID clientId) {
        return bonusServiceClient.getAccount(clientId);
    }

    @Override
    public String getParameter(String parameterName) {
        return bonusServiceParametersClient.getParameter(parameterName);
    }
}
