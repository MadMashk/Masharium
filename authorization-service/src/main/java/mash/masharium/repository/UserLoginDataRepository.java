package mash.masharium.repository;

import mash.masharium.entity.UserDevice;
import mash.masharium.entity.UserLoginData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserLoginDataRepository extends JpaRepository<UserLoginData, UUID>, BaseRepository {

    String ERROR_MESSAGE = "Данные об учетной записи пользователя не найдены";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
    Optional<UserLoginData> findByLogin(String login);

    Optional<UserLoginData> findByUserDevice(UserDevice device);
}
