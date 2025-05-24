package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.RefundRepositoryGateway;
import br.com.portaldbv.domain.entities.Refund;
import br.com.portaldbv.infra.mapper.RefundMapper;
import br.com.portaldbv.infra.persistence.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RefundGatewayImpl implements RefundRepositoryGateway {

    private final RefundRepository repository;
    private final RefundMapper mapper;

    @Override
    public Refund getById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Refund com ID " + id + " não encontrado."));
    }

    @Override
    public List<Refund> getAll(Long clubId, UUID userId, Long eventId, LocalDate startDate, LocalDate endDate, Integer page, Integer size) {
        return repository
                .findAllByClubWithFilters(clubId, eventId, userId, startDate, endDate, PageRequest.of(page, size))
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public Refund save(Refund refund) {
        return mapper.toDomain(repository.save(mapper.toEntity(refund)));
    }

    @Override
    public void delete(Refund refund) {
        repository.delete(mapper.toEntity(refund));
    }
}
