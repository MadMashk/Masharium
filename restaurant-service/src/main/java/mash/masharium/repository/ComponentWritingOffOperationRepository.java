package mash.masharium.repository;

import mash.masharium.entity.ComponentWritingOffOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ComponentWritingOffOperationRepository extends JpaRepository<ComponentWritingOffOperation, UUID>, BaseRepository {
    String ERROR_MESSAGE = "Компонент операции списанию не найден";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
}
