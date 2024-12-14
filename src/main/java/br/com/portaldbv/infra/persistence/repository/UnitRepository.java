package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.infra.persistence.entities.UnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<UnitEntity, Long> {
    Optional<UnitEntity> getUnitEntityById(Long id);

    List<UnitEntity> getUnitEntityByClubId(Long id);

    Optional<UnitEntity> getUnitEntityByNameAndClubId(String name, Long id);
}
