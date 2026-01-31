package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.ActivityRecordRepositoryGateway;
import br.com.portaldbv.application.gateways.ActivityRepositoryGateway;
import br.com.portaldbv.domain.dto.ActivityPointsMetricsDTO;
import br.com.portaldbv.domain.entities.Activity;
import br.com.portaldbv.domain.entities.ActivityRecord;
import br.com.portaldbv.domain.enums.error.ActivityErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class ActivityRecordUseCases {

    private final ActivityRecordRepositoryGateway repository;
    private final ActivityRepositoryGateway activityRepository;
    private final UnitUseCases unitUseCases;
    private final UserUseCases userUseCases;

    public ActivityRecord getById(Long id) {
        return Optional.ofNullable(repository.getById(id)).orElseThrow(() -> new DomainException(ActivityErrorEnum.ID_NOT_FOUND));
    }

    public List<ActivityRecord> getRecordsByUnitId(Long unitId) {

        var records = repository.getByUnitId(unitId);
        records.sort(Comparator.comparing(ActivityRecord::getCreatedDate).reversed());

        return records;
    }

    public List<ActivityRecord> getRecordsByUnitIdAndDate(Long unitId, LocalDate date) {

        var records = repository.getByUnitIdAndDateEquals(unitId, date);
        records.sort(Comparator.comparing(ActivityRecord::getCreatedDate).reversed());

        return records;
    }

    public List<ActivityPointsMetricsDTO> getTotalPoints(Long clubId) {

        var units = unitUseCases.getAllByClub(clubId);

        if (units.isEmpty()) throw new DomainException(ActivityErrorEnum.NOT_EXIST);

        var totalPoints = new ArrayList<ActivityPointsMetricsDTO>();

        units.forEach(unit -> {
            var total = 0;
            var records = repository.getByUnitId(unit.getId());

            if (records.isEmpty()) {
                totalPoints.add(new ActivityPointsMetricsDTO(unit, total));
            } else {
                for (var record : records) {
                    total = total + record.getPoints();
                }
                totalPoints.add(new ActivityPointsMetricsDTO(unit, total));
            }
        });

        return totalPoints;
    }

    public ActivityPointsMetricsDTO getTotalPointsByUnit(Long unitId) {

        AtomicInteger total = new AtomicInteger();

        var unit = unitUseCases.getById(unitId);

        var records = repository.getByUnitId(unitId);

        if (!records.isEmpty()) records.forEach(record -> total.set(total.get() + record.getPoints()));

        return new ActivityPointsMetricsDTO(unit, total.get());
    }


    public ActivityRecord register(ActivityRecord record, Long unitId, Long activityId, UUID registeredByUser) {

        var unit = unitUseCases.getById(unitId);

        Activity activity = null;

        if (activityId != null) {

            activity = activityRepository.getById(activityId);

            if (repository.getByUnitIdAndDateEqualsAndActivityId(unitId, LocalDate.now(ZoneId.of("America/Sao_Paulo")), activity.getId()).isPresent())
                throw new DomainException(ActivityErrorEnum.ALREADY_REGISTERED);
        }

        record.setActivity(activity);
        record.setUnit(unit);
        record.setRegisteredByUser(userUseCases.getById(registeredByUser));

        return repository.register(record);

    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

}
