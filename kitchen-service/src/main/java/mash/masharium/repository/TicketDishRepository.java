package mash.masharium.repository;

import mash.masharium.entity.TicketDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TicketDishRepository extends JpaRepository<TicketDish, UUID> {
}
