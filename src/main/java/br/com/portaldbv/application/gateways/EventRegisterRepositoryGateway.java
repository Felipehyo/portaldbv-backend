package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.EventRegister;
import br.com.portaldbv.domain.enums.UserTypeEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventRegisterRepositoryGateway {

    Optional<EventRegister> getByEventIdAndUserId(Long eventId, UUID userId);

    List<EventRegister> getAllByEventRegisterIdAndUserTypes(Long eventId, List<UserTypeEnum> userTypes);

    EventRegister save(EventRegister eventRegister);

    void delete(EventRegister eventRegister);

}
