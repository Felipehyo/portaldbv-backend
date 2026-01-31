package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.CashBookRepositoryGateway;
import br.com.portaldbv.domain.entities.CashBook;
import br.com.portaldbv.infra.mapper.CashBookMapper;
import br.com.portaldbv.infra.persistence.repository.CashBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CashBookGatewayImpl implements CashBookRepositoryGateway {

    private final CashBookRepository cashBookRepository;
    private final CashBookMapper mapper;

    @Override
    public CashBook getById(UUID id) {
        return cashBookRepository
                .findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("CashBook com ID " + id + " não encontrado."));
    }

    @Override
    public List<CashBook> getAllByClubId(Long clubId, Long eventId, LocalDate startDate, LocalDate endDate, Integer page, Integer size) {
        return cashBookRepository
                .findAllByClubWithFilters(clubId, eventId, startDate, endDate, PageRequest.of(page, size))
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public CashBook save(CashBook cashBook) {
        return mapper.toDomain(cashBookRepository.save(mapper.toEntity(cashBook)));
    }

    @Override
    public void delete(CashBook cashBook) {
        cashBookRepository.delete(mapper.toEntity(cashBook));
    }

}
