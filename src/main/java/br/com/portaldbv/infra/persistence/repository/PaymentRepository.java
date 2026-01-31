package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.infra.persistence.entities.PaymentEntity;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, UUID> {

    List<PaymentEntity> findByClubId(@PathParam("club_id") Long id);

    @Query("SELECT p FROM PaymentEntity p WHERE p.club.id = :clubId " +
            "AND (COALESCE(:userId, NULL) IS NULL OR p.user.id = :userId) " +
            "AND (:eventId IS NULL OR p.event.id = :eventId) " +
            "AND (:startDate IS NULL AND :endDate IS NULL OR p.date BETWEEN :startDate AND :endDate)")
    Page<PaymentEntity> findAllByClubWithFilters(
            @Param("clubId") Long clubId,
            @Param("userId") UUID userId,
            @Param("eventId") Long eventId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

    List<PaymentEntity> findByUserId(UUID id);

}
