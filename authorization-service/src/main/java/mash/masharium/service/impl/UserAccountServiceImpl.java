package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.auth.request.SingUpRequest;
import mash.masharium.entity.UserAccount;
import mash.masharium.repository.UserAccountRepository;
import mash.masharium.service.UserAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {
    private final UserAccountRepository userAccountRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public UserAccount createNewUserAccount(SingUpRequest singUpRequest) {
        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(singUpRequest.firstName());
        userAccount.setLastName(singUpRequest.lastName());
        userAccount.setEmail(singUpRequest.email());
        userAccount.setDateOfBirth(singUpRequest.dateOfBirth());
        userAccount.setPhoneNumber(singUpRequest.phoneNumber());
        return userAccountRepository.save(userAccount);
    }
}
