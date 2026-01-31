package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.infra.persistence.entities.ActivityRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ActivityRecordRepository extends JpaRepository<ActivityRecordEntity, Long> {

    List<ActivityRecordEntity> findByUnitId(Long unitId);

    List<ActivityRecordEntity> findByActivityClubId(Long clubId);

    List<Optional<ActivityRecordEntity>> findByUnitIdAndDateEquals(Long unitId, LocalDate date);

    Optional<ActivityRecordEntity> findByUnitIdAndDateEqualsAndActivityId(Long unitId, LocalDate date, Long activityId);

}