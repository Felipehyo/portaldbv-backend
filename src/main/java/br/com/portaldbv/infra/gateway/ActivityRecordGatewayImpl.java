package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.ActivityRecordRepositoryGateway;
import br.com.portaldbv.application.gateways.CashBookRepositoryGateway;
import br.com.portaldbv.domain.entities.Activity;
import br.com.portaldbv.domain.entities.ActivityRecord;
import br.com.portaldbv.domain.entities.CashBook;
import br.com.portaldbv.infra.mapper.ActivityMapper;
import br.com.portaldbv.infra.mapper.ActivityRecordMapper;
import br.com.portaldbv.infra.mapper.CashBookMapper;
import br.com.portaldbv.infra.persistence.repository.ActivityRecordRepository;
import br.com.portaldbv.infra.persistence.repository.ActivityRepository;
import br.com.portaldbv.infra.persistence.repository.CashBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityRecordGatewayImpl implements ActivityRecordRepositoryGateway {

    private final ActivityRecordRepository repository;
    private final ActivityRecordMapper mapper;

    @Override
    public ActivityRecord getById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Atividade com ID " + id + " não encontrado."));
    }

    @Override
    public List<ActivityRecord> getByUnitId(Long id) {
        return repository.findByUnitId(id).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<ActivityRecord> getByUnitIdAndDateEquals(Long id, LocalDate date) {
        return repository.findByUnitId(id).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ActivityRecord> getByUnitIdAndDateEqualsAndActivityId(Long unitId, LocalDate date, Long activityId) {
        return repository.findByUnitIdAndDateEqualsAndActivityId(unitId, date, activityId).map(mapper::toDomain);
    }

    @Override
    public ActivityRecord register(ActivityRecord request) {
        return mapper.toDomain(repository.save(mapper.toEntity(request)));
    }

    @Override
    public void delete(ActivityRecord request) {
        repository.delete(mapper.toEntity(request));
    }

}
