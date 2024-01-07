package mash.masharium.repository;

import mash.masharium.entity.BonusAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BonusAccountRepository extends JpaRepository<BonusAccount, UUID> {
    Optional<BonusAccount> findByUserId(UUID userId);
}
