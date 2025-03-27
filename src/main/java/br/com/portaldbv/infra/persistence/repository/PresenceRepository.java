package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.infra.persistence.entities.PresenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PresenceRepository extends JpaRepository<PresenceEntity, Long> {
    Optional<PresenceEntity> getPresenceEntityById(Long id);

    List<PresenceEntity> getPresenceEntityByClubId(Long clubId);

    List<PresenceEntity> getPresenceEntityByUserId(UUID userId);
    List<PresenceEntity> getPresenceEntityByClubIdAndUserActiveAndDate(Long clubId, Boolean active, LocalDate date);
    Optional<PresenceEntity> getPresenceEntityByUserIdAndUserActiveAndDate(UUID userId, Boolean active, LocalDate date);
}
