package mash.masharium.repository;

import mash.masharium.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ComponentRepository extends JpaRepository<Component, UUID>, BaseRepository {

    String ERROR_MESSAGE = "Ингредиент не найден";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }

}
