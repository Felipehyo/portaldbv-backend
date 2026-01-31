package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.ActivityRepositoryGateway;
import br.com.portaldbv.domain.entities.Activity;
import br.com.portaldbv.domain.entities.ActivityRecord;
import br.com.portaldbv.domain.enums.error.ActivityErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ActivityUseCases {

    private final ActivityRepositoryGateway repository;
    private final ClubUseCases clubUseCases;
    private final UnitUseCases unitUseCases;
    private final ActivityRecordUseCases activityRecordUseCases;

    public Activity getById(Long id) {
        return Optional.ofNullable(repository.getById(id)).orElseThrow(() -> new DomainException(ActivityErrorEnum.ID_NOT_FOUND));
    }

    public List<Activity> getAllByClub(Long clubId) {
        return repository.getAllByClubId(clubId);
    }

    public List<Activity> getDiaryRecordsByUnitId(Long unitId) {
        var unit = unitUseCases.getById(unitId);
        var allRecords = activityRecordUseCases.getRecordsByUnitIdAndDate(unitId, LocalDate.now(ZoneId.of("America/Sao_Paulo")));
        var activities = repository.getAllByClubId(unit.getClubId());

        for (Activity activity : repository.getAllByClubId(unit.getClubId())) {
            for (ActivityRecord record : allRecords) {
                if (record.getActivity() != null && Objects.equals(record.getActivity().getId(), activity.getId()) && record.getDate().equals(LocalDate.now(ZoneId.of("America/Sao_Paulo"))) && !activity.getAlwaysDisplay()) {
                    activities.remove(activity);
                    break;
                }
            }
        }

        return activities;
    }


    public Activity register(Activity activity, Long clubId) {

        if (repository.getByName(activity.getName()).isPresent())
            throw new DomainException(ActivityErrorEnum.ALREADY_REGISTERED);

        activity.setClub(clubUseCases.getById(clubId));

        return activity;

    }

    public Activity update(Long id, Activity activity) {

        var oldActivity = getById(id);

        if (!StringUtils.isBlank(activity.getName())) oldActivity.setName(activity.getName());
        if (!StringUtils.isBlank(activity.getDescription())) oldActivity.setDescription(activity.getDescription());
        if (activity.getMerit() != null && activity.getMerit() >= 0) oldActivity.setMerit(activity.getMerit());
        if (activity.getDemerit() != null && activity.getDemerit() >= 0) oldActivity.setDemerit(activity.getDemerit());
        if (activity.getActivityOrder() != null) oldActivity.setActivityOrder(activity.getActivityOrder());
        if (activity.getAlwaysDisplay() != null) oldActivity.setAlwaysDisplay(activity.getAlwaysDisplay());

        return repository.save(oldActivity);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

}
