package mash.masharium.service.impl;

import lombok.RequiredArgsConstructor;
import mash.masharium.entity.UserDevice;
import mash.masharium.entity.UserLoginData;
import mash.masharium.exception.NotFountException;
import mash.masharium.repository.UserDeviceRepository;
import mash.masharium.service.UserDeviceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserDeviceServiceImpl implements UserDeviceService {

    private final UserDeviceRepository userDeviceRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public UserDevice createNewUserDevice(String deviceInfo, UserLoginData userLoginData) {
        UserDevice userDevice = new UserDevice();
        userDevice.setDeviceInfo(deviceInfo);
        userDevice.setIsConnected(true);
        userDevice.setUser(userLoginData);
        userDevice.setEnterDate(Instant.now());
        return userDeviceRepository.save(userDevice);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDevice getByUUID(UUID uuid) {
        return userDeviceRepository.findById(uuid)
                .orElseThrow(() -> new NotFountException(userDeviceRepository.getErrorMassage()));
    }

    @Override
    @Transactional
    public void disconnectDevice(UserDevice userDevice) {
        userDevice.setIsConnected(false);
        userDeviceRepository.save(userDevice);
    }
}
