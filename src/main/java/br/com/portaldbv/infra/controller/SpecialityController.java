package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.SpecialityUseCases;
import br.com.portaldbv.infra.dto.request.speciality.SpecialityRequestDTO;
import br.com.portaldbv.infra.mapper.SpecialityMapper;
import br.com.portaldbv.infra.resource.SpecialityResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class SpecialityController implements SpecialityResource {

    private final SpecialityUseCases useCases;
    private final SpecialityMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    public SpecialityController(SpecialityUseCases useCases, SpecialityMapper mapper) {
        this.useCases = useCases;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Object> getAll(String category) {
        var specialities = useCases.getAll(category);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toReponseList(specialities));
    }

    @Override
    public ResponseEntity<Object> getById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<Object> register(MultipartFile file, String specialityRequestJson) throws JsonProcessingException {
        SpecialityRequestDTO specialityRequest = objectMapper.readValue(specialityRequestJson, SpecialityRequestDTO.class);
        var speciality = useCases.register(mapper.toDomain(specialityRequest), file);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(speciality));
    }

    @Override
    public ResponseEntity<Object> update(Long id, MultipartFile file, SpecialityRequestDTO specialityRequest) {
        var speciality = useCases.update(id, mapper.toDomain(specialityRequest), file);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(speciality));
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}