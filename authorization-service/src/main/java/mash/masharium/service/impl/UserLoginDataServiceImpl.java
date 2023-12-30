package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.auth.request.Credentials;
import mash.masharium.entity.UserAccount;
import mash.masharium.entity.UserDevice;
import mash.masharium.entity.UserLoginData;
import mash.masharium.exception.NotFountException;
import mash.masharium.repository.UserLoginDataRepository;
import mash.masharium.service.UserLoginDataService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserLoginDataServiceImpl implements UserLoginDataService {

    private final UserLoginDataRepository userLoginDataRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public UserLoginData createNewUserLoginData(Credentials credentials, UserAccount userAccount) {
        UserLoginData userLoginData = new UserLoginData();
        userLoginData.setLogin(credentials.login());
        userLoginData.setPassword(passwordEncoder.encode(credentials.password()));
        userLoginData.setIsLogged(true);
        userLoginData.setUserAccount(userAccount);
        return userLoginDataRepository.save(userLoginData);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkIfUserIsRegistered(String login) {
        return userLoginDataRepository.findByLogin(login).isPresent();
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkIfCredentialsAreValid(Credentials credentials) {
        Optional<UserLoginData> userLoginData = userLoginDataRepository.findByLogin(credentials.login());

        return userLoginData.isPresent() && passwordEncoder.matches(credentials.password(), userLoginData.get().getPassword());
    }

    @Transactional(readOnly = true)
    public UserLoginData getByLogin(String login) {
        return userLoginDataRepository.findByLogin(login)
                .orElseThrow(() -> new NotFountException(userLoginDataRepository.getErrorMassage()));
    }

    @Override
    @Transactional(readOnly = true)
    public UserLoginData getByDevice(UserDevice userDevice) {
        return userLoginDataRepository.findByUserDevice(userDevice)
                .orElseThrow(() -> new NotFountException(userLoginDataRepository.getErrorMassage()));
    }

    @Override
    @Transactional
    public void logUser(UserLoginData userLoginData) {
        userLoginData.setIsLogged(true);
        userLoginDataRepository.save(userLoginData);
    }

    @Override
    @Transactional
    public void logoutUser(UserLoginData userLoginData) {
        userLoginData.setIsLogged(false);
        userLoginDataRepository.save(userLoginData);
    }
}
