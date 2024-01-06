package mash.masharium.repository;

import mash.masharium.entity.DishComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DishComponentRepository extends JpaRepository<DishComponent, UUID>, BaseRepository {

    String ERROR_MESSAGE = "Ингредиент блюда не найден";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }


}
