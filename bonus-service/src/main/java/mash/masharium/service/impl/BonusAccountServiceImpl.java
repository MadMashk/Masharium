package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.bonus.request.CreateBonusRequest;
import mash.masharium.api.bonus.response.BonusAccountResponseDto;
import mash.masharium.api.bonus.constant.OperationStatus;
import mash.masharium.api.bonus.constant.OperationType;
import mash.masharium.api.bonus.response.OperationsHistoryDto;
import mash.masharium.entity.BonusAccount;
import mash.masharium.entity.BonusOperation;
import mash.masharium.exception.model.NotFoundException;
import mash.masharium.mapper.BonusAccountMapper;
import mash.masharium.mapper.BonusOperationMapper;
import mash.masharium.repository.BonusAccountRepository;
import mash.masharium.service.BonusAccountService;
import mash.masharium.service.BonusOperationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BonusAccountServiceImpl implements BonusAccountService {

    private final BonusAccountRepository bonusAccountRepository;

    private final BonusAccountMapper bonusAccountMapper;

    private final BonusOperationService bonusOperationService;

    private final BonusOperationMapper bonusOperationMapper;

    @Override
    @Transactional
    public UUID create(UUID userId) {
        Optional<BonusAccount> bonusAccountOptional = bonusAccountRepository.findByUserId(userId);
        if (bonusAccountOptional.isPresent()) {
            return bonusAccountOptional.get().getId();
        }
        BonusAccount bonusAccount = new BonusAccount();
        bonusAccount.setUserId(userId);
        bonusAccount.setBalance(0);
        bonusAccount.setOperations(new ArrayList<>());
        return bonusAccountRepository.save(bonusAccount).getId();
    }

    @Override
    @Transactional
    public void accrual(UUID userId, UUID orderId, Integer summa) {
        if (summa <= 0) {
            return;
        }
        BonusAccount bonusAccount = getBonusAccountWithCreationOperation(userId, orderId, OperationType.ACCRUAL, summa);
        bonusAccount.setBalance(bonusAccount.getBalance() + summa);
    }

    @Override
    @Transactional(readOnly = true)
    public BonusAccountResponseDto get(UUID userId) {
        return bonusAccountMapper.toDto(getBonusAccount(userId));
    }

    @Override
    @Transactional
    public void writeOff(UUID userId, UUID orderId, Integer summa) {
        if (summa <= 0) {
            return;
        }
        BonusAccount bonusAccount = getBonusAccountWithCreationOperation(userId, orderId, OperationType.WRITING_OFF, summa);
        bonusAccount.setBalance(bonusAccount.getBalance() - summa);
    }

    @Override
    @Transactional(readOnly = true)
    public OperationsHistoryDto getOperations(UUID userId) {
        BonusAccount bonusAccount = getBonusAccount(userId);
        OperationsHistoryDto response = new OperationsHistoryDto();
        response.setOperations(bonusOperationMapper.toListDto(bonusAccount.getOperations()));
        return response;
    }

    private BonusAccount getBonusAccount(UUID userId) {
        return bonusAccountRepository.findByUserId(userId).orElseThrow(
                () -> new NotFoundException("Не найден счет клиента " + userId)
        );
    }

    private BonusAccount getBonusAccountWithCreationOperation(UUID userId, UUID orderId, OperationType operationType, Integer summa) {
        BonusAccount bonusAccount = getBonusAccount(userId);
        BonusOperation bonusOperation = new BonusOperation();
        bonusOperation.setAccountId(bonusAccount.getId());
        bonusOperation.setType(operationType);
        bonusOperation.setTime(LocalDateTime.now());
        bonusOperation.setOrderId(orderId);
        bonusOperation.setSumma(summa);
        bonusOperation.setStatus(OperationStatus.DONE);
        bonusOperationService.save(bonusOperation);
        return bonusAccount;
    }
}
