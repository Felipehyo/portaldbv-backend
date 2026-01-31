package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.Event;
import br.com.portaldbv.infra.dto.event.EventRequestDTO;
import br.com.portaldbv.infra.dto.event.EventResponseDTO;
import br.com.portaldbv.infra.persistence.entities.EventEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventEntity toEntity(Event event);

    Event toDomain(EventEntity eventEntity);

    Event toDomain(EventRequestDTO eventRequestDTO);

    EventResponseDTO toDTO(Event event);

    List<Event> toDomainList(List<EventEntity> eventEntities);
}
