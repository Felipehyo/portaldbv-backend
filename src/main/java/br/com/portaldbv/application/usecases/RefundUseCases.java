package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.RefundRepositoryGateway;
import br.com.portaldbv.domain.entities.Refund;
import br.com.portaldbv.domain.enums.error.CashBookErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class RefundUseCases {

    private final RefundRepositoryGateway repository;
    private final ClubUseCases clubUseCases;
    private final EventUseCases eventUseCases;
    private final UserUseCases userUseCases;

    public Refund getById(Long id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(CashBookErrorEnum.ID_NOT_FOUND));
    }

    public List<Refund> getAll(Long clubId, UUID userId, Long eventId, LocalDate startDate, LocalDate endDate, Integer page, Integer size) {
        return repository.getAll(clubId, userId, eventId, startDate, endDate, page, size);
    }

    public Refund register(Refund refund, Long clubId, Long eventId, UUID userId) {

        var club = clubUseCases.getById(clubId);
        var event = (eventId != null) ? eventUseCases.getById(eventId) : null;
        var user = userUseCases.getById(userId);

        refund.setClub(club);
        refund.setEvent(event);
        refund.setUser(user);

        return repository.save(refund);
    }

    public Refund changeStatus(Long refundId, Boolean isRefunded) {

        var refund = getById(refundId);

        refund.setAlreadyRefunded(isRefunded);

        return repository.save(refund);
    }

    public void delete(Long id) {
        var refund = getById(id);
        repository.delete(refund);
    }

}
