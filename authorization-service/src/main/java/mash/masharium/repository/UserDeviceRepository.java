package mash.masharium.repository;

import mash.masharium.entity.UserDevice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserDeviceRepository extends JpaRepository<UserDevice, UUID>, BaseRepository {

    String ERROR_MESSAGE = "Устройство пользователя не найдено";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
}
