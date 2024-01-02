package mash.masharium.repository;

import mash.masharium.entity.BonusOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BonusOperationRepository extends JpaRepository<BonusOperation, UUID> {
}
