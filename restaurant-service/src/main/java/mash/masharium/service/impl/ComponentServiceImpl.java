package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.restaurant.common.ComponentDto;
import mash.masharium.api.restaurant.request.ComponentCreationRequest;
import mash.masharium.entity.Component;
import mash.masharium.entity.ComponentWritingOffOperation;
import mash.masharium.entity.WritingOffOperation;
import mash.masharium.exception.InvalidTransactionException;
import mash.masharium.exception.NotFountException;
import mash.masharium.mapper.ComponentMapper;
import mash.masharium.repository.ComponentRepository;
import mash.masharium.service.ComponentService;
import mash.masharium.service.WritingOffOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;
    private final ComponentMapper componentMapper;
    private final WritingOffOperationService writingOffOperationService;

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

        WritingOffOperation writingOffOperation = writingOffOperationService.createWritingOffOperation(orderId);

        return componentMapper.componentsToComponentDtos(componentQuantityToWriteOffMap.keySet().stream().peek(component -> {
            BigDecimal currentQuantity = component.getQuantity();
            BigDecimal quantityToWriteOff = componentQuantityToWriteOffMap.get(component);
            BigDecimal newQuantity = currentQuantity.subtract(quantityToWriteOff);
            if (newQuantity.compareTo(BigDecimal.ZERO) < 1) {
                throw new InvalidTransactionException("Недостаточно компонентов на складе");
            }

            component.setQuantity(newQuantity);
            componentRepository.save(component);

            ComponentWritingOffOperation componentWritingOffOperation = new ComponentWritingOffOperation();
            componentWritingOffOperation.setQuantity(quantityToWriteOff);
            componentWritingOffOperation.setQuantityBefore(currentQuantity);
            componentWritingOffOperation.setQuantityAfter(newQuantity);
            componentWritingOffOperation.setComponent(component);
            componentWritingOffOperation.setWritingOffOperation(writingOffOperation);
            writingOffOperationService.createComponentOfWritingOfOperation(componentWritingOffOperation);
        }).toList());


    }

}
