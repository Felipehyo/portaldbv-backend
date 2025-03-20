package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepositoryGateway {

    List<Event> getAllByClubIdAndFilters(Long clubId, Boolean onlyActives);

    Event getById(Long id);

    List<Event> getByClubIdAndDate(Long clubId, LocalDate initialDate, LocalDate finalDate);

    Event getByClubIdAndDateAndName(Long clubId, LocalDate initialDate, LocalDate finalDate, String name);

    Event register(Event event);

    Event update(Event event);

    void delete(Event event);

}
