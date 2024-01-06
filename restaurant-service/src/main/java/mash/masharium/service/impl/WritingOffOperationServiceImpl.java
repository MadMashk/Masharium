package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.entity.ComponentWritingOffOperation;
import mash.masharium.entity.WritingOffOperation;
import mash.masharium.repository.ComponentWritingOffOperationRepository;
import mash.masharium.repository.WritingOffOperationRepository;
import mash.masharium.service.WritingOffOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WritingOffOperationServiceImpl implements WritingOffOperationService {

    private final WritingOffOperationRepository writingOffOperationRepository;
    private final ComponentWritingOffOperationRepository componentWritingOffOperationRepository;

    @Override
    @Transactional
    public ComponentWritingOffOperation createComponentOfWritingOfOperation(ComponentWritingOffOperation componentWritingOffOperation) {
        return componentWritingOffOperationRepository.save(componentWritingOffOperation);
    }

    @Override
    @Transactional
    public WritingOffOperation createWritingOffOperation(UUID orderId) {
        WritingOffOperation writingOffOperation = new WritingOffOperation();
        writingOffOperation.setOrderId(orderId);
        writingOffOperation.setDate(Instant.now());
        return writingOffOperationRepository.save(writingOffOperation);
    }
}
