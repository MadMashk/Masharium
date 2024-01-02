package mash.masharium.repository;

import mash.masharium.entity.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, UUID> {

    Optional<Parameter> findByName(String name);
}
