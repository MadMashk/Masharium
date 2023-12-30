package mash.masharium.service;

import mash.masharium.entity.UserDevice;
import mash.masharium.entity.UserLoginData;

import java.util.UUID;

public interface UserDeviceService {

    UserDevice createNewUserDevice(String deviceInfo, UserLoginData userLoginData);

    UserDevice getByUUID(UUID uuid);

    void disconnectDevice(UserDevice userDevice);

}
