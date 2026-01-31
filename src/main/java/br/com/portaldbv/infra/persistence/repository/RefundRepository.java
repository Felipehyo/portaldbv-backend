package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.infra.persistence.entities.RefundEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface RefundRepository extends JpaRepository<RefundEntity, Long> {

    @Query("SELECT c FROM RefundEntity c WHERE c.club.id = :clubId " +
            "AND (:eventId IS NULL OR c.event.id = :eventId) " +
            "AND (COALESCE(:userId, NULL) IS NULL OR c.user.id = :userId) " +
            "AND (:startDate IS NULL AND :endDate IS NULL OR c.refundedDate BETWEEN :startDate AND :endDate)")
    List<RefundEntity> findAllByClubWithFilters(
            @Param("clubId") Long clubId,
            @Param("eventId") Long eventId,
            @Param("userId") UUID userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

}