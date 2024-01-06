package mash.masharium.repository;

import mash.masharium.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface MenuRepository extends JpaRepository<Menu, UUID>, BaseRepository {

    String ERROR_MESSAGE = "Меню не найдено";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
}
