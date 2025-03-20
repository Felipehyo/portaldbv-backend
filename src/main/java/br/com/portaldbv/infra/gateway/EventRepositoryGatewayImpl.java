package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.EventRepositoryGateway;
import br.com.portaldbv.domain.entities.Event;
import br.com.portaldbv.infra.mapper.EventMapper;
import br.com.portaldbv.infra.persistence.entities.EventEntity;
import br.com.portaldbv.infra.persistence.repository.EventRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EventRepositoryGatewayImpl implements EventRepositoryGateway {

    private final EventRepository eventRepository;
    private final EventMapper mapper;

    @Override
    public List<Event> getAllByClubIdAndFilters(Long clubId, Boolean onlyActives) {
        List<EventEntity> events = eventRepository.getEventEntityByClubIdAndFilters(clubId, onlyActives);
        return mapper.toDomainList(events);
    }

    @Override
    public Event getById(Long id) {
        Optional<EventEntity> entity = eventRepository.getEventEntityById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Event> getByClubIdAndDate(Long clubId, LocalDate initialDate, LocalDate finalDate) {
        List<EventEntity> events = eventRepository.getEventEntityByClubIdAndDateBetween(clubId, initialDate, finalDate);
        return mapper.toDomainList(events);
    }

    @Override
    public Event getByClubIdAndDateAndName(Long clubId, LocalDate initialDate, LocalDate finalDate, String name) {
        EventEntity event = eventRepository.getEventEntityByClubIdAndNameAndDateBetween(clubId, name, initialDate, finalDate);
        return mapper.toDomain(event);
    }

    @Override
    public Event register(Event event) {
        EventEntity entity = mapper.toEntity(event);
        return mapper.toDomain(eventRepository.save(entity));
    }

    @Override
    public Event update(Event event) {
        return mapper.toDomain(eventRepository.save(mapper.toEntity(event)));
    }

    @Override
    public void delete(Event event) {
        eventRepository.delete(mapper.toEntity(event));
    }

}
