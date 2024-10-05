package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.ClubUseCases;
import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.infra.dto.request.ClubRequestDTO;
import br.com.portaldbv.infra.mapper.ClubMapper;
import br.com.portaldbv.infra.resource.ClubResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ClubController implements ClubResource {

    private final ClubUseCases useCases;
    private final ClubMapper mapper;

    public ClubController(ClubUseCases useCases, ClubMapper mapper) {
        this.useCases = useCases;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Object> register(ClubRequestDTO clubRequest) {
        Club club = useCases.register(mapper.toDomain(clubRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.paraDTO(club));
    }

    @Override
    public ResponseEntity<Object> getById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.paraDTO(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<Object> getByName(String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.paraDTO(useCases.getByName(name)));
    }
}