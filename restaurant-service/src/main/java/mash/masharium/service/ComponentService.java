package mash.masharium.service;

import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.request.ComponentCreationRequest;
import mash.masharium.entity.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ComponentService {
    Component findById(UUID componentId);

    List<ComponentDto> createComponents(List<ComponentCreationRequest> componentCreationRequest);

    List<ComponentDto> writeOffComponents(Map<Component, BigDecimal> componentQuantityToWriteOffMap, UUID orderId);

    List<ComponentDto> accrualComponents(Map<Component, BigDecimal> componentQuantityToWriteOffMap, UUID orderId);
}
