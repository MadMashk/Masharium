package mash.masharium.repository;

import mash.masharium.entity.TableReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReservationRepository extends JpaRepository<TableReservation, UUID>, BaseRepository {

    String ERROR_MESSAGE = "Резервация не найдена не найдено";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }
}
