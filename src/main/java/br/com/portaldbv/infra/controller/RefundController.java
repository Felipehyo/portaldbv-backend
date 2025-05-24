package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.RefundUseCases;
import br.com.portaldbv.domain.entities.Refund;
import br.com.portaldbv.infra.dto.request.refund.RefundRequestDTO;
import br.com.portaldbv.infra.mapper.RefundMapper;
import br.com.portaldbv.infra.resource.RefundResource;
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
public class RefundController implements RefundResource {

    private final RefundUseCases useCases;
    private final RefundMapper mapper;

    @Override
    public ResponseEntity<Object> getAll(Long clubId, LocalDate startDate, LocalDate endDate, UUID userId, Long eventId, Pageable pageable) {
        List<Refund> refunds = useCases.getAll(clubId, userId, eventId, startDate, endDate, pageable.getPageNumber(), pageable.getPageSize());
        return ResponseEntity.status(HttpStatus.OK).body(refunds);
    }

    @Override
    public ResponseEntity<Object> getById(Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(useCases.getById(id));
    }

    @Override
    public ResponseEntity<Object> register(RefundRequestDTO request) {
        var refund = useCases.register(mapper.toDomain(request), request.clubId(), request.eventId(), request.userId());
        return ResponseEntity.status(HttpStatus.CREATED).body(refund);
    }

    @Override
    public ResponseEntity<Object> delete(Long id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}