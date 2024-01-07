package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.api.auth.common.Jwt;
import mash.masharium.api.auth.response.UserInfoResponse;
import mash.masharium.entity.UserAccount;
import mash.masharium.entity.UserDevice;
import mash.masharium.entity.UserLoginData;
import mash.masharium.mapper.UserInfoMapper;
import mash.masharium.service.UserDeviceService;
import mash.masharium.service.UserLoginDataService;
import mash.masharium.service.UserService;
import mash.masharium.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserService {
    private final UserDeviceService userDeviceService;
    private final UserLoginDataService userLoginDataService;
    private final JwtUtils jwtUtils;

    private final UserInfoMapper userInfoMapper;

    @Override
    public UserInfoResponse prepareUserInfos(String jwt) {
        UUID deviceId = jwtUtils.getIdFromJWTToken(jwt);
        UserLoginData userLoginData = userLoginDataService.getByDevice(userDeviceService.getByUUID(deviceId));
        List<UserDevice> userDeviceList = userLoginData.getUserDevice();
        UserAccount userAccount = userLoginData.getUserAccount();
        return userInfoMapper.mapToUserInfoResponse(userAccount, userLoginData, userDeviceList);
    }

}
