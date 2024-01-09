package mash.masharium.repository;

import mash.masharium.entity.Ticket;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    Optional<Ticket> findByOrderId(UUID orderId);

    @Query(value = "select t.* from tickets t where t.ticket_status <> 'DONE'", nativeQuery = true)
    List<Ticket> findAllActive();
}
