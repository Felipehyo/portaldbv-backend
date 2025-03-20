package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.EventRegisterRepositoryGateway;
import br.com.portaldbv.domain.entities.EventRegister;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.infra.mapper.EventRegisterMapper;
import br.com.portaldbv.infra.persistence.entities.EventRegisterEntity;
import br.com.portaldbv.infra.persistence.repository.EventRegisterRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class EventRegisterRepositoryGatewayImpl implements EventRegisterRepositoryGateway {

    private final EventRegisterRepository repository;
    private final EventRegisterMapper mapper;

    @Override
    public Optional<EventRegister> getByEventIdAndUserId(Long eventId, UUID userId) {
        Optional<EventRegisterEntity> entity = repository.getEventRegisterEntityByEventIdAndUserId(eventId, userId);
        return Optional.of(entity.map(mapper::toDomain)).orElse(null);
    }

    @Override
    public List<EventRegister> getAllByEventRegisterIdAndUserTypes(Long eventId, List<UserTypeEnum> userTypes) {
        List<EventRegisterEntity> events = repository.getEventRegisterEntityByEventIdAndUserTypeIn(eventId, userTypes);
        return mapper.toDomainList(events);
    }

    @Override
    public EventRegister save(EventRegister eventRegister) {
        return mapper.toDomain(repository.save(mapper.toEntity(eventRegister)));
    }

    @Override
    public void delete(EventRegister eventRegister) {
        repository.delete(mapper.toEntity(eventRegister));
    }
}
