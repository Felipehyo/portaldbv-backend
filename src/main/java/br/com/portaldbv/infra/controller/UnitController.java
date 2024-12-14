package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.UnitUseCases;
import br.com.portaldbv.infra.dto.request.unit.UnitRequestDTO;
import br.com.portaldbv.infra.mapper.UnitMapper;
import br.com.portaldbv.infra.resource.UnitResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UnitController implements UnitResource {

    private final UnitUseCases useCases;
    private final UnitMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    public UnitController(UnitUseCases useCases, UnitMapper mapper) {
        this.useCases = useCases;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Object> getAllByClubId(Long clubId) {
        var units = useCases.getAllByClub(clubId);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toReponseList(units));
    }

    @Override
    public ResponseEntity<Object> getById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<Object> register(MultipartFile file, String unitRequestJson) throws JsonProcessingException {
        UnitRequestDTO unitRequest = objectMapper.readValue(unitRequestJson, UnitRequestDTO.class);
        var unit = useCases.register(mapper.toDomain(unitRequest), file);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(unit));
    }

    @Override
    public ResponseEntity<Object> update(Long id, MultipartFile file, String unitRequestJson) throws JsonProcessingException {
        UnitRequestDTO unitRequest = objectMapper.readValue(unitRequestJson, UnitRequestDTO.class);
        var unit = useCases.update(id, mapper.toDomain(unitRequest), file);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(unit));
    }

    @Override
    public ResponseEntity<Object> addPoints(Long id, Integer qtdPoints) {
        useCases.addPoints(id, qtdPoints);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> removePoints(Long id, Integer qtdPoints) {
        useCases.removePoints(id, qtdPoints);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> addPendencyPoints(Long id, Integer qtdPoints) {
        useCases.addPendencyPoints(id, qtdPoints);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> removePendencyPoints(Long id, Integer qtdPoints) {
        useCases.removePendencyPoints(id, qtdPoints);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}