package mash.masharium.service;

import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.response.UserInfoResponse;

public interface UserService {

    UserInfoResponse prepareUserInfos(String jwt);

}
