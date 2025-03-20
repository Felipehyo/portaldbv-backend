package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.EventRegister;
import br.com.portaldbv.infra.persistence.entities.EventRegisterEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventRegisterMapper {

    EventRegisterEntity toEntity(EventRegister eventRegister);

    EventRegister toDomain(EventRegisterEntity eventRegister);

    List<EventRegister> toDomainList(List<EventRegisterEntity> eventEntities);
}
