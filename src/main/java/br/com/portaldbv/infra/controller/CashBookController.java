package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.CashBookUseCases;
import br.com.portaldbv.domain.entities.CashBook;
import br.com.portaldbv.infra.dto.cashbook.CashBookRequestDTO;
import br.com.portaldbv.infra.dto.cashbook.CashBookResponseDTO;
import br.com.portaldbv.infra.mapper.CashBookMapper;
import br.com.portaldbv.infra.resource.CashBookResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CashBookController implements CashBookResource {

    private final CashBookUseCases useCases;
    private final CashBookMapper mapper;

    @Override
    public ResponseEntity<CashBookResponseDTO> getById(UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<List<CashBookResponseDTO>> getAllByClubId(Long clubId, Long eventId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        List<CashBook> cashBookList = useCases.getAllByClubId(clubId, eventId, startDate, endDate, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDTOList(cashBookList));
    }

    @Override
    public ResponseEntity<CashBookResponseDTO> register(CashBookRequestDTO cashBook, Long clubId, Long eventId) {
        var response = useCases.register(mapper.toDomain(cashBook), clubId, eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(response));
    }

    @Override
    public ResponseEntity<CashBookResponseDTO> update(UUID id, CashBookRequestDTO request, Long eventId) {
        var response = useCases.update(id, mapper.toDomain(request), eventId);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(response));
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}