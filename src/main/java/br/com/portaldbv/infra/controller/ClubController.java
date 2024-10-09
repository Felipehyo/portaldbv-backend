package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.ClubUseCases;
import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.infra.dto.request.club.BankRequestDTO;
import br.com.portaldbv.infra.dto.request.club.ClubRequestDTO;
import br.com.portaldbv.infra.mapper.ClubMapper;
import br.com.portaldbv.infra.resource.ClubResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ClubController implements ClubResource {

    private final ClubUseCases useCases;
    private final ClubMapper mapper;

    public ClubController(ClubUseCases useCases, ClubMapper mapper) {
        this.useCases = useCases;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity<Object> getAll() {
        List<Club> clubs = useCases.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(clubs);
    }

    @Override
    public ResponseEntity<Object> getById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.paraDTO(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<Object> register(ClubRequestDTO clubRequest) {
        Club club = useCases.register(mapper.toDomain(clubRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.paraDTO(club));
    }

    @Override
    public ResponseEntity<Object> update(Long id, ClubRequestDTO clubRequest) {
        var club = useCases.update(id, mapper.toDomain(clubRequest));
        return ResponseEntity.status(HttpStatus.OK).body(mapper.paraDTO(club));
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> deposit(Long id, BankRequestDTO bankRequest) {
        useCases.deposit(id, bankRequest.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<Object> withdraw(Long id, BankRequestDTO bankRequest) {
        useCases.withdraw(id, bankRequest.value());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}