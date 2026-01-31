package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.CashBook;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CashBookRepositoryGateway {

    CashBook getById(UUID id);

    List<CashBook> getAllByClubId(Long clubId, Long eventId, LocalDate startDate, LocalDate endDate, Integer page, Integer size);

    CashBook save(CashBook cashBook);

    void delete(CashBook cashBook);

}
