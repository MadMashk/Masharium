package mash.masharium.repository;

import mash.masharium.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


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
}
