package mash.masharium.repository;

import mash.masharium.api.order.constant.OrderStatus;
import mash.masharium.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query(value = "select o.* from orders o where client_id = :clientId and status = :status", nativeQuery = true)
    Optional<Order> findByClientIdAndStatus(UUID clientId, String status);

    List<Order> findAllByClientId(UUID clientId);

    @Query(value = "select o.* from orders o where o.status not in ('DONE', 'CLOSED') or (o.status = 'DONE' and o.is_paid = false)", nativeQuery = true)
    List<Order> findAllActive();

    @Query(value = "select o.* from orders o where o.status = 'DONE' " +
            "and o.is_paid = true " +
            "and o.end_time >= :firstDate " +
            "and o.end_time <= :secondDate " +
            "order by o.end_time asc", nativeQuery = true)
    List<Order> findAllPaidAndCompletedBetweenDates(LocalDateTime firstDate, LocalDateTime secondDate);

    Optional<Order> findByClientIdOrWaiterIdAndStatus(UUID clientId, UUID waiterId, OrderStatus name);
}
