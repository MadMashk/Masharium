package mash.masharium.repository;

import mash.masharium.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRoleRepository extends JpaRepository<UserRole, UUID>, BaseRepository {
    String ERROR_MESSAGE = "Данные о роли пользователя не найдены";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
}
