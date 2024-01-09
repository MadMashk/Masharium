package mash.masharium.repository;

import mash.masharium.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, UUID>, BaseRepository {

    String ERROR_MESSAGE = "Стол не найден";

    @Override
    default String getErrorMassage() {
        return ERROR_MESSAGE;
    }

    @Query(value = "select t1.* from restaurant_table t1 where t1.id not in (select t2.id from restaurant_table t2 right join table_reservation r on t2.id = r.restaurant_table_id " +
            "where :endTime > r.reservation_date and :time < r.reservation_end)", nativeQuery = true)
    List<RestaurantTable> findFreeInTime(LocalDateTime time, LocalDateTime endTime);
}
