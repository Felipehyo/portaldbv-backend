package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Refund;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RefundRepositoryGateway {

    Refund getById(Long id);

    List<Refund> getAll(Long clubId, UUID userId, Long eventId, LocalDate startDate, LocalDate endDate, Integer page, Integer size);

    Refund save(Refund cashBook);

    void delete(Refund cashBook);

}
