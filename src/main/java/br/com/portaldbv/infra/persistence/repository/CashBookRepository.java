package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.infra.persistence.entities.CashBookEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface CashBookRepository extends JpaRepository<CashBookEntity, UUID> {

    @Query("SELECT c FROM CashBookEntity c WHERE c.club.id = :clubId " +
            "AND (:eventId IS NULL OR c.event.id = :eventId) " +
            "AND (:startDate IS NULL AND :endDate IS NULL OR c.date BETWEEN :startDate AND :endDate) order by c.date DESC")
    List<CashBookEntity> findAllByClubWithFilters(
            @Param("clubId") Long clubId,
            @Param("eventId") Long eventId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);

}