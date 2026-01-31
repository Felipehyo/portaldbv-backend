package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.PaymentRepositoryGateway;
import br.com.portaldbv.domain.entities.Payment;
import br.com.portaldbv.infra.dto.PaginatedResponse;
import br.com.portaldbv.infra.mapper.PaymentMapper;
import br.com.portaldbv.infra.persistence.entities.PaymentEntity;
import br.com.portaldbv.infra.persistence.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PaymentRepositoryGatewayImpl implements PaymentRepositoryGateway {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    @Override
    public PaginatedResponse<Payment> getAllByClubWithFilters(Long clubId, LocalDate initialDate, LocalDate finalDate, UUID userId, Long eventId, Integer page, Integer size) {
        var payments = repository.findAllByClubWithFilters(clubId, userId, eventId, initialDate, finalDate, PageRequest.of(page, size));
        PaginatedResponse<Payment> response = new PaginatedResponse<>();
        response.setContent(mapper.toDomainList(payments.getContent()));
        response.setPage(payments.getPageable().getPageNumber());
        response.setSize(payments.getPageable().getPageSize());
        response.setTotalPages(payments.getTotalPages());
        response.setTotalElements(payments.getTotalElements());
        payments.getContent();
        return response;
    }

    @Override
    public Payment getById(UUID id) {
        Optional<PaymentEntity> entity = repository.findById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public Payment register(Payment payment) {
        PaymentEntity entity = mapper.toEntity(payment);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public void delete(Payment payment) {
        repository.delete(mapper.toEntity(payment));
    }

}
