package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.common.UserDetailsDto;
import mash.masharium.api.auth.constant.UserRoleType;
import mash.masharium.api.auth.request.SignInRequest;
import mash.masharium.api.auth.request.SingUpRequest;
import mash.masharium.api.bonus.request.CreateBonusRequest;
import mash.masharium.entity.UserAccount;
import mash.masharium.entity.UserDevice;
import mash.masharium.entity.UserLoginData;
import mash.masharium.entity.UserRole;
import mash.masharium.exception.AuthValidationException;
import mash.masharium.integration.client.BonusServiceClient;
import mash.masharium.service.AuthService;
import mash.masharium.service.RoleService;
import mash.masharium.service.UserAccountService;
import mash.masharium.service.UserDeviceService;
import mash.masharium.service.UserLoginDataService;
import mash.masharium.utils.JwtUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAccountService userAccountService;

    private final RoleService roleService;

    private final UserDeviceService userDeviceService;

    private final UserLoginDataService userLoginDataService;

    private final BonusServiceClient bonusServiceClient;

    private final JwtUtils jwtUtils;

    @Override
    @Transactional
    public Jwt singupUser(SingUpRequest singUpRequest) {

        if (Boolean.TRUE.equals(userLoginDataService.checkIfUserIsRegistered(singUpRequest.credentials().login()))) {
            throw new AuthValidationException(String.format("Пользователь с логином %s уже зарегистрирован в системе", singUpRequest.credentials().login()));
        }

        UserAccount userAccount = userAccountService.createNewUserAccount(singUpRequest);

        UserLoginData userLoginData = userLoginDataService.createNewUserLoginData(singUpRequest.credentials(), userAccount);

        UserDevice userDevice = userDeviceService.createNewUserDevice(singUpRequest.deviceInfo(), userLoginData);

        roleService.createUserRoles(singUpRequest.roles(), userLoginData);

        bonusServiceClient.create(userLoginData.getId().toString());

        return new Jwt(jwtUtils.createToken(userDevice.getId()));
    }

    @Override
    @Transactional
    public Jwt signInUser(SignInRequest signInRequest) {

        if (Boolean.FALSE.equals(userLoginDataService.checkIfCredentialsAreValid(signInRequest.credentials()))) {
            throw new AuthValidationException("Неправильный логин или пароль");
        }

        UserLoginData userLoginData = userLoginDataService.getByLogin(signInRequest.credentials().login());

        userLoginDataService.logUser(userLoginData);

        UserDevice newUserDevice = userDeviceService.createNewUserDevice(signInRequest.deviceInfo(), userLoginData);

        return new Jwt(jwtUtils.createToken(newUserDevice.getId()));
    }

    @Override
    @Transactional
    public UserDetailsDto validateToken(Jwt token) {
        try {
            jwtUtils.validateToken(token.token());
            UserDevice userDevice = userDeviceService.getByUUID(jwtUtils.getIdFromJWTToken(token.token()));
            return getUserDetailsDto(userLoginDataService.getByDevice(userDevice));
        } catch (Exception e) {
            throw new AuthValidationException("Ошибка валидации токена");
        }
    }

    private UserDetailsDto getUserDetailsDto(UserLoginData userLoginData) { //todo mapper
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setUsername(userLoginData.getLogin());
        userDetailsDto.setEmail(userLoginData.getUserAccount().getEmail());
        userDetailsDto.setId(userLoginData.getId());
        List<UserRoleType> userRoleTypes = userLoginData.getUserRoles().stream().map(UserRole::getUserRoleType).toList();
        userDetailsDto.setUserRoleTypes(userRoleTypes);
        return userDetailsDto;
    }

    @Override
    @Transactional
    public void logoutUser(Jwt token) {

        UserDevice userDevice = userDeviceService.getByUUID(jwtUtils.getIdFromJWTToken(token.token()));

        userDeviceService.disconnectDevice(userDevice);

        userLoginDataService.logoutUser(userLoginDataService.getByDevice(userDevice));

    }
}
