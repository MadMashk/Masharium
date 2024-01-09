package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.constant.ComponentQuantityChangingOperationType;
import mash.masharium.api.restaurant.request.ComponentCreationRequest;
import mash.masharium.entity.Component;
import mash.masharium.entity.ComponentQuantityChangingOperation;
import mash.masharium.entity.ComponentQuantityChangingOperationComponent;
import mash.masharium.exception.InvalidTransactionException;
import mash.masharium.exception.NotFountException;
import mash.masharium.mapper.ComponentMapper;
import mash.masharium.repository.ComponentRepository;
import mash.masharium.service.ComponentQuantityChangingOperationService;
import mash.masharium.service.ComponentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BinaryOperator;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    private final ComponentMapper componentMapper;
    private final ComponentQuantityChangingOperationService componentQuantityChangingOperationService;

    @Override
    @Transactional(readOnly = true)
    public Component findById(UUID componentId) {
        return componentRepository.findById(componentId)
                .orElseThrow(() -> new NotFountException(componentRepository.getErrorMassage()));
    }

    @Override
    @Transactional
    public List<ComponentDto> createComponents(List<ComponentCreationRequest> componentCreationRequests) {
        return componentMapper.componentsToComponentDtos(componentRepository
                .saveAll(componentMapper.mapComponentCreationRequestsToComponents(componentCreationRequests)));
    }


    @Override
    @Transactional
    public List<ComponentDto> writeOffComponents(Map<Component, BigDecimal> componentQuantityToWriteOffMap, UUID orderId) {
        ComponentQuantityChangingOperation componentQuantityChangingOperation = componentQuantityChangingOperationService
                .createComponentQuantityChangingOperation(orderId, ComponentQuantityChangingOperationType.WRITE_OFF);
        return changeQuantity(componentQuantityToWriteOffMap, componentQuantityChangingOperation, BigDecimal::subtract);

    }

    @Override
    @Transactional
    public List<ComponentDto> accrualComponents(Map<Component, BigDecimal> componentQuantityToWriteOffMap, UUID orderId) {
        ComponentQuantityChangingOperation componentQuantityChangingOperation = componentQuantityChangingOperationService
                .createComponentQuantityChangingOperation(orderId, ComponentQuantityChangingOperationType.ACCRUAL);
        return changeQuantity(componentQuantityToWriteOffMap, componentQuantityChangingOperation, BigDecimal::add);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Component> getAll() {
        return componentRepository.findAll();
    }

    private List<ComponentDto> changeQuantity(Map<Component, BigDecimal> componentQuantityToWriteOffMap,
                                              ComponentQuantityChangingOperation componentQuantityChangingOperation,
                                              BinaryOperator<BigDecimal> mathFunction) {

        return componentMapper.componentsToComponentDtos(componentQuantityToWriteOffMap.keySet().stream().peek(component -> {
            BigDecimal currentQuantity = component.getQuantity();
            BigDecimal quantityToChange = componentQuantityToWriteOffMap.get(component);
            BigDecimal newQuantity = mathFunction.apply(currentQuantity, quantityToChange);

            if (newQuantity.compareTo(BigDecimal.ZERO) < 1
                    && componentQuantityChangingOperation.getChangingOperationType().equals(ComponentQuantityChangingOperationType.WRITE_OFF)) {
                throw new InvalidTransactionException("Недостаточно компонентов на складе");
            }
            component.setQuantity(newQuantity);
            componentRepository.save(component);

            createOperation(quantityToChange, currentQuantity, newQuantity, component, componentQuantityChangingOperation);

        }).toList());


    }

    private void createOperation(BigDecimal quantityToWriteOff, BigDecimal currentQuantity, BigDecimal newQuantity, Component component, ComponentQuantityChangingOperation componentQuantityChangingOperation) { //todo mapper
        ComponentQuantityChangingOperationComponent componentQuantityChangingOperationComponent = new ComponentQuantityChangingOperationComponent();
        componentQuantityChangingOperationComponent.setQuantity(quantityToWriteOff);
        componentQuantityChangingOperationComponent.setQuantityBefore(currentQuantity);
        componentQuantityChangingOperationComponent.setQuantityAfter(newQuantity);
        componentQuantityChangingOperationComponent.setComponent(component);
        componentQuantityChangingOperationComponent.setWritingOffOperation(componentQuantityChangingOperation);
        componentQuantityChangingOperationService.createQuantityChangingOperationComponent(componentQuantityChangingOperationComponent);
    }

}
