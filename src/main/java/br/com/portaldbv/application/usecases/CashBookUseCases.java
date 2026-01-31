package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.CashBookRepositoryGateway;
import br.com.portaldbv.domain.entities.CashBook;
import br.com.portaldbv.domain.enums.BookTypeEnum;
import br.com.portaldbv.domain.enums.error.CashBookErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CashBookUseCases {

    private final CashBookRepositoryGateway repository;
    private final ClubUseCases clubUseCases;
    private final EventUseCases eventUseCases;

    public CashBook getById(UUID id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(CashBookErrorEnum.ID_NOT_FOUND));
    }

    public List<CashBook> getAllByClubId(Long clubId, Long eventId, LocalDate startDate, LocalDate endDate, Integer page, Integer size) {
        return repository.getAllByClubId(clubId, eventId, startDate, endDate, page, size);
    }

    public CashBook register(CashBook cashBook, Long clubId, Long eventId) {

        var club = clubUseCases.getById(clubId);
        var event = (eventId != null) ? eventUseCases.getById(eventId) : null;

        cashBook.setClub(club);
        cashBook.setEvent(event);

        if (BookTypeEnum.INPUT.equals(cashBook.getType())) {
            clubUseCases.deposit(clubId, cashBook.getValue());
        } else {
            clubUseCases.subtract(clubId, cashBook.getValue());
        }

        return repository.save(cashBook);
    }

    public CashBook update(UUID id, CashBook request, Long eventId) {

        var oldCashBook = getById(id);
        var club = oldCashBook.getClub();

        if (request.getType() != null) {
            if (BookTypeEnum.INPUT.equals(request.getType())) {
                if (oldCashBook.getType() == request.getType()) {
                    if (request.getValue() != null && !request.getValue().equals(oldCashBook.getValue())) {
                        clubUseCases.subtract(club.getId(), oldCashBook.getValue());
                        oldCashBook.setValue(request.getValue());
                        clubUseCases.deposit(club.getId(), request.getValue());
                    }
                } else {
                    oldCashBook.setType(request.getType());
                    if (request.getValue() != null) {
                        clubUseCases.deposit(club.getId(), oldCashBook.getValue());
                        oldCashBook.setValue(request.getValue());
                        clubUseCases.deposit(club.getId(), request.getValue());
                    }
                }
            } else if (BookTypeEnum.OUTPUT.equals(request.getType())) {
                if (oldCashBook.getType() == request.getType()) {
                    if (request.getValue() != null && !request.getValue().equals(oldCashBook.getValue())) {
                        clubUseCases.deposit(club.getId(), oldCashBook.getValue());
                        oldCashBook.setValue(request.getValue());
                        clubUseCases.subtract(club.getId(), request.getValue());
                    }
                } else {
                    oldCashBook.setType(request.getType());
                    if (request.getValue() != null) {
                        clubUseCases.subtract(club.getId(), oldCashBook.getValue());
                        oldCashBook.setValue(request.getValue());
                        clubUseCases.subtract(club.getId(), request.getValue());
                    }
                }
            }
        } else if (request.getValue() != null && !request.getValue().equals(oldCashBook.getValue())) {
            if (BookTypeEnum.INPUT.equals(oldCashBook.getType())) {
                clubUseCases.subtract(club.getId(), oldCashBook.getValue());
                oldCashBook.setValue(request.getValue());
                clubUseCases.deposit(club.getId(), request.getValue());
            } else if (BookTypeEnum.OUTPUT.equals(oldCashBook.getType())) {
                clubUseCases.deposit(club.getId(), oldCashBook.getValue());
                oldCashBook.setValue(request.getValue());
                clubUseCases.subtract(club.getId(), request.getValue());
            }
        }

        if (!StringUtils.isBlank(request.getDescription())) oldCashBook.setDescription(request.getDescription());
        if (request.getDate() != null) oldCashBook.setDate(request.getDate());

        oldCashBook.setEvent((eventId != null) ? eventUseCases.getById(eventId) : null);

        return repository.save(oldCashBook);

    }

    public void delete(UUID id) {

        var cashBook = getById(id);

        if (BookTypeEnum.INPUT.equals(cashBook.getType())) {
            clubUseCases.subtract(cashBook.getClub().getId(), cashBook.getValue());
        } else {
            clubUseCases.deposit(cashBook.getClub().getId(), cashBook.getValue());
        }

        repository.delete(cashBook);
    }

}
