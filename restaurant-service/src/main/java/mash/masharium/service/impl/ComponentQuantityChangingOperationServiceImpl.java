package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.constant.ComponentQuantityChangingOperationType;
import mash.masharium.entity.ComponentQuantityChangingOperation;
import mash.masharium.entity.ComponentQuantityChangingOperationComponent;
import mash.masharium.repository.ComponentQuantityChangingOperationComponentRepository;
import mash.masharium.repository.ComponentQuantityChangingOperationRepository;
import mash.masharium.service.ComponentQuantityChangingOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComponentQuantityChangingOperationServiceImpl implements ComponentQuantityChangingOperationService {

    private final ComponentQuantityChangingOperationRepository componentQuantityChangingOperationRepository;
    private final ComponentQuantityChangingOperationComponentRepository componentQuantityChangingOperationComponentRepository;

    @Override
    @Transactional
    public ComponentQuantityChangingOperationComponent createQuantityChangingOperationComponent(ComponentQuantityChangingOperationComponent componentWritingOffOperation) {
        return componentQuantityChangingOperationComponentRepository.save(componentWritingOffOperation);
    }

    @Override
    @Transactional
    public ComponentQuantityChangingOperation createComponentQuantityChangingOperation(UUID orderId, ComponentQuantityChangingOperationType componentQuantityChangingOperationType) {
        ComponentQuantityChangingOperation writingOffOperation = new ComponentQuantityChangingOperation();
        writingOffOperation.setOrderId(orderId);
        writingOffOperation.setDate(Instant.now());
        writingOffOperation.setChangingOperationType(componentQuantityChangingOperationType);
        return componentQuantityChangingOperationRepository.save(writingOffOperation);
    }
}
