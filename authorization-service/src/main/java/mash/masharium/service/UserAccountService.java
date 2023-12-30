package mash.masharium.service;

import mash.masharium.api.auth.request.SingUpRequest;
import mash.masharium.entity.UserAccount;

public interface UserAccountService {

    UserAccount createNewUserAccount(SingUpRequest singUpRequest);

}
