package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.entity.BonusOperation;
import mash.masharium.repository.BonusOperationRepository;
import mash.masharium.service.BonusOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BonusOperationServiceImpl implements BonusOperationService {

    private final BonusOperationRepository bonusOperationRepository;

    @Override
    @Transactional
    public void save(BonusOperation bonusOperation) {
        bonusOperationRepository.save(bonusOperation);
    }
}
