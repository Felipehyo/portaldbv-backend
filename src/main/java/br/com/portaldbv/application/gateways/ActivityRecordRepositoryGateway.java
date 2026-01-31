package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.ActivityRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ActivityRecordRepositoryGateway {

    ActivityRecord getById(Long id);

    List<ActivityRecord> getByUnitId(Long id);

    List<ActivityRecord> getByUnitIdAndDateEquals(Long id, LocalDate date);

    Optional<ActivityRecord> getByUnitIdAndDateEqualsAndActivityId(Long unitId, LocalDate date, Long activityId);

    ActivityRecord register(ActivityRecord activityRecord);

    void delete(ActivityRecord activityRecord);

}