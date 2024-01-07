package mash.masharium.repository;

import mash.masharium.entity.ComponentQuantityChangingOperationComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComponentQuantityChangingOperationComponentRepository extends JpaRepository<ComponentQuantityChangingOperationComponent, UUID>, BaseRepository {
    String ERROR_MESSAGE = "Компонент операции списанию не найден";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
}
