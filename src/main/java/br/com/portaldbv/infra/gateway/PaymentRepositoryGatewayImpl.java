package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.PaymentRepositoryGateway;
import br.com.portaldbv.domain.entities.Payment;
import br.com.portaldbv.infra.mapper.PaymentMapper;
import br.com.portaldbv.infra.persistence.entities.PaymentEntity;
import br.com.portaldbv.infra.persistence.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PaymentRepositoryGatewayImpl implements PaymentRepositoryGateway {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    @Override
    public List<Payment> getAllByClubWithFilters(Long clubId, LocalDate initialDate, LocalDate finalDate, UUID userId, Long eventId, Integer page, Integer size) {
        List<PaymentEntity> payments = repository.findAllByClubWithFilters(clubId, userId, eventId, initialDate, finalDate, PageRequest.of(page, size));
        return mapper.toDomainList(payments);
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
