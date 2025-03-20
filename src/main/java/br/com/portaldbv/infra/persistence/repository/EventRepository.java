package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.infra.persistence.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Long> {
    Optional<EventEntity> getEventEntityById(Long id);

    List<EventEntity> getEventEntityByClubIdAndDateBetween(Long id, LocalDate initialDate, LocalDate finalDate);

    EventEntity getEventEntityByClubIdAndNameAndDateBetween(Long id, String name, LocalDate initialDate, LocalDate finalDate);

    Optional<EventEntity> getEventEntityByNameAndClubId(String name, Long id);

    @Query("SELECT u FROM EventEntity u WHERE u.club.id = :clubId " +
            "AND (:onlyActives = FALSE OR u.active = TRUE) ")
    List<EventEntity> getEventEntityByClubIdAndFilters(
            @Param("clubId") Long clubId,
            @Param("onlyActives") Boolean onlyActives
    );
}
