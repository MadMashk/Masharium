package mash.masharium.repository;

import mash.masharium.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserAccountRepository extends JpaRepository<UserAccount, UUID>, BaseRepository {

    String ERROR_MESSAGE = "Профиль пользователя не найден";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
}
