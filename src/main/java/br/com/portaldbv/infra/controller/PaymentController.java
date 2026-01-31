package br.com.portaldbv.infra.controller;

import br.com.portaldbv.application.usecases.PaymentUseCases;
import br.com.portaldbv.infra.dto.payment.PaymentRequestDTO;
import br.com.portaldbv.infra.mapper.PaymentMapper;
import br.com.portaldbv.infra.resource.PaymentResource;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentController implements PaymentResource {

    private final PaymentUseCases useCases;
    private final PaymentMapper mapper;

    @Override
    public ResponseEntity<Object> getAllByClubWithFilters(Long clubId, LocalDate startDate, LocalDate endDate, UUID userId, Long eventId, Pageable pageable) {
        var payments = useCases.getAllByClubWithFilters(clubId, startDate, endDate, userId, eventId, pageable.getPageNumber(), pageable.getPageSize());

        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponseList(payments));
    }

    @Override
    public ResponseEntity<Object> getById(UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toDetailResponse(useCases.getById(id)));
    }

    @Override
    public ResponseEntity<Object> register(PaymentRequestDTO paymentRequest) {
        var user = useCases.register(mapper.toDomain(paymentRequest), paymentRequest.clubId(), paymentRequest.userId(), paymentRequest.eventId());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(user));
    }

    @Override
    public ResponseEntity<Object> delete(UUID id) {
        useCases.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}