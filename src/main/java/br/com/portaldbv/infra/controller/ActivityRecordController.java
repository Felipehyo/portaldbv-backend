package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.ActivityRecordUseCases;
import br.com.portaldbv.domain.dto.ActivityPointsMetricsDTO;
import br.com.portaldbv.domain.entities.ActivityRecord;
import br.com.portaldbv.infra.dto.activity.record.ActivityRecordRequestDTO;
import br.com.portaldbv.infra.dto.activity.record.ActivityRecordResponseDTO;
import br.com.portaldbv.infra.mapper.ActivityRecordMapper;
import br.com.portaldbv.infra.resource.ActivityRecordResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ActivityRecordController implements ActivityRecordResource {

    private final ActivityRecordUseCases useCases;
    private final ActivityRecordMapper mapper;

    @Override
    public ResponseEntity<ActivityRecordResponseDTO> getById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<List<ActivityRecordResponseDTO>> getRecordsByUnitId(Long unitId) {
        List<ActivityRecord> list = useCases.getRecordsByUnitId(unitId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTOList(list));
    }

    @Override
    public ResponseEntity<List<ActivityPointsMetricsDTO>> getTotalPoints(Long clubId) {
        return ResponseEntity.status(HttpStatus.OK).body(useCases.getTotalPoints(clubId));
    }

    @Override
    public ResponseEntity<ActivityPointsMetricsDTO> getTotalPointsByUnit(Long unitId) {
        return ResponseEntity.status(HttpStatus.OK).body(useCases.getTotalPointsByUnit(unitId));
    }

    @Override
    public ResponseEntity<ActivityRecordResponseDTO> register(ActivityRecordRequestDTO request, Long unitId, Long activityId, UUID registeredByUser) {
        var response = useCases.register(mapper.toDomain(request), unitId, activityId, registeredByUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(response));
    }

    @Override
    public ResponseEntity<ActivityRecordResponseDTO> update(UUID id, ActivityRecordRequestDTO request) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}