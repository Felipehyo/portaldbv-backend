package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.EventRepositoryGateway;
import br.com.portaldbv.domain.entities.Event;
import br.com.portaldbv.domain.enums.error.EventErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EventUseCases {

    private final EventRepositoryGateway repository;
    private final ClubUseCases clubUseCases;

    public List<Event> getAllByClub(Long clubId, Boolean onlyActives) {
        return Optional.ofNullable(repository.getAllByClubIdAndFilters(clubId, onlyActives))
                .orElseThrow(() -> new DomainException(EventErrorEnum.NOT_FOUND));
    }

    public List<Event> getAllByClubAndDate(Long clubId, LocalDate initialDate, LocalDate finalDate) {
        return Optional.ofNullable(repository.getByClubIdAndDate(clubId, initialDate, finalDate))
                .orElseThrow(() -> new DomainException(EventErrorEnum.NOT_FOUND));
    }

    public Event getById(Long id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(EventErrorEnum.ID_NOT_FOUND));
    }

    public Event register(Event event, Long clubId) {

        var year = event.getDate().getYear();

        event.setClub(clubUseCases.getById(clubId));
        event.setBank(BigDecimal.ZERO);
        event.setActive(Boolean.TRUE);

        if (repository.getByClubIdAndDateAndName(clubId, LocalDate.of(year, Month.JANUARY, 1), LocalDate.of(year, Month.DECEMBER, 31), event.getName()) != null) {
            throw new DomainException(EventErrorEnum.ALREADY_REGISTERED);
        }

        return repository.register(event);
    }

    public Event update(Long id, Event event) {
        var oldEvent = getById(id);

        oldEvent.setDate(event.getDate());
        oldEvent.setValue(event.getValue());
        oldEvent.setName(event.getName());
        oldEvent.setActive(event.getActive());

        return repository.update(oldEvent);
    }

    public void activeOrInactive(Long id, Boolean active) {
        var oldEvent = getById(id);
        oldEvent.setActive(active);
        repository.update(oldEvent);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

    public void deposit(Long id, BigDecimal value) {
        var event = getById(id);
        event.setBank(event.getBank().add(value));
        repository.update(event);
    }

    public void subtract(Long id, BigDecimal value) {
        var event = getById(id);
        event.setBank(event.getBank().subtract(value));
        repository.update(event);
    }

}
