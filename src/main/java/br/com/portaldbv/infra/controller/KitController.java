package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.KitUseCases;
import br.com.portaldbv.domain.entities.Kit;
import br.com.portaldbv.infra.dto.kit.KitRequestDTO;
import br.com.portaldbv.infra.mapper.KitMapper;
import br.com.portaldbv.infra.resource.KitResource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KitController implements KitResource {

    private final KitUseCases useCases;
    private final KitMapper mapper;

    @Override
    public ResponseEntity<Object> getById(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<Object> getAllByUserId(UUID userId) {
        List<Kit> kits = useCases.getAllByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponseList(kits));
    }

    @Override
    public ResponseEntity<Object> register(UUID userId, KitRequestDTO request) {
        Kit kit = useCases.register(userId, mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(kit));
    }

    @Override
    public ResponseEntity<Object> metricsByUser(UUID userId) {
        var metrics = useCases.getMetricsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(metrics);
    }

    @Override
    public ResponseEntity<Object> allMetricsByClub(Long clubId) {
        var metrics = useCases.getAllMetricsByClub(clubId);
        return ResponseEntity.status(HttpStatus.OK).body(metrics);
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}