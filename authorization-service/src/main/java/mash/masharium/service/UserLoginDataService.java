package mash.masharium.service;

import mash.masharium.api.auth.request.Credentials;
import mash.masharium.entity.UserAccount;
import mash.masharium.entity.UserDevice;
import mash.masharium.entity.UserLoginData;

public interface UserLoginDataService {

    UserLoginData createNewUserLoginData(Credentials credentials, UserAccount userAccount);

    Boolean checkIfUserIsRegistered(String login);

    Boolean checkIfCredentialsAreValid(Credentials credentials);

    UserLoginData getByLogin(String login);

    void logUser(UserLoginData userLoginData);

    UserLoginData getByDevice(UserDevice userDevice);

    void logoutUser(UserLoginData userLoginData);
}
