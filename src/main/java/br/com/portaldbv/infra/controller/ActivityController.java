package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.ActivityUseCases;
import br.com.portaldbv.domain.entities.Activity;
import br.com.portaldbv.infra.dto.activity.ActivityRequestDTO;
import br.com.portaldbv.infra.dto.activity.ActivityResponseDTO;
import br.com.portaldbv.infra.mapper.ActivityMapper;
import br.com.portaldbv.infra.resource.ActivityResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ActivityController implements ActivityResource {

    private final ActivityUseCases useCases;
    private final ActivityMapper mapper;

    @Override
    public ResponseEntity<ActivityResponseDTO> getById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<List<ActivityResponseDTO>> getAllByClubId(Long clubId) {
        List<Activity> list = useCases.getAllByClub(clubId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTOList(list));
    }

    @Override
    public ResponseEntity<List<ActivityResponseDTO>> getDiaryActivitiesByUnitId(Long clubId, Long unitId) {
        List<Activity> list = useCases.getDiaryRecordsByUnitId(unitId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTOList(list));
    }

    @Override
    public ResponseEntity<ActivityResponseDTO> register(ActivityRequestDTO request, Long clubId) {
        var response = useCases.register(mapper.toDomain(request), clubId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(response));
    }

    @Override
    public ResponseEntity<ActivityResponseDTO> update(Long id, ActivityRequestDTO request) {
        var response = useCases.update(id, mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(response));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}