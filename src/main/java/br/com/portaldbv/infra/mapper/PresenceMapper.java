package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.dto.PresencePercentageMetricsDTO;
import br.com.portaldbv.domain.entities.Presence;
import br.com.portaldbv.infra.dto.presence.PresenceRequestDTO;
import br.com.portaldbv.infra.dto.presence.PresencePercentageMetricsBasicDataResponseDTO;
import br.com.portaldbv.infra.dto.presence.PresenceResponseDTO;
import br.com.portaldbv.infra.dto.presence.PresenceTodayDTO;
import br.com.portaldbv.infra.persistence.entities.PresenceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PresenceMapper {

    PresenceEntity toEntity(Presence presence);

    Presence toDomain(PresenceEntity presenceEntity);

    Presence toDomain(PresenceRequestDTO presenceRequestDTO);

    PresenceResponseDTO toResponse(Presence presence);

    List<Presence> toDomainList(List<PresenceEntity> presenceEntities);

    List<PresenceResponseDTO> toResponseList(List<Presence> presences);

    @Mapping(source = "presenceType", target = "status")
    PresenceTodayDTO toTodayResponse(Presence presence);

    List<PresenceTodayDTO> toTodayResponseList(List<Presence> presences);

    List<PresencePercentageMetricsBasicDataResponseDTO> toMetricsResponseList(List<PresencePercentageMetricsDTO> presences);

}
