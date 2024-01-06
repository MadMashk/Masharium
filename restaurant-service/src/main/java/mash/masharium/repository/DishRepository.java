package mash.masharium.repository;

import mash.masharium.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface DishRepository extends JpaRepository<Dish, UUID>, BaseRepository {
    String ERROR_MESSAGE = "Блюдо не найдено";
    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }

    List<Dish> findAllByIdIn(Set<UUID> dishUuids);
}
