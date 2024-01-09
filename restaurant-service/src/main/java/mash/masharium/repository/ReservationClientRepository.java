package mash.masharium.repository;

import mash.masharium.entity.TableClient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationClientRepository extends JpaRepository<TableClient, UUID>, BaseRepository {

    String ERROR_MESSAGE = "Резервация стол клиент не найдена";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
}
