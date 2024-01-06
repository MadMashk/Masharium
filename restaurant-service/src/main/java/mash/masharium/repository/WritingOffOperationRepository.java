package mash.masharium.repository;

import mash.masharium.entity.Dish;
import mash.masharium.entity.WritingOffOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WritingOffOperationRepository extends JpaRepository<WritingOffOperation, UUID>, BaseRepository{
    String ERROR_MESSAGE = "Операция по списанию компонента не найдена";
    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
}
