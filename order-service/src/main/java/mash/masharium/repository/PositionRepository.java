package mash.masharium.repository;

import mash.masharium.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {

}
