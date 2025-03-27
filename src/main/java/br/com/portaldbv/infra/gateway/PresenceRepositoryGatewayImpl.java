package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.PresenceRepositoryGateway;
import br.com.portaldbv.domain.entities.Presence;
import br.com.portaldbv.infra.mapper.PresenceMapper;
import br.com.portaldbv.infra.persistence.entities.PresenceEntity;
import br.com.portaldbv.infra.persistence.repository.PresenceRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class PresenceRepositoryGatewayImpl implements PresenceRepositoryGateway {

    private final PresenceRepository repository;
    private final PresenceMapper mapper;

    @Override
    public List<Presence> getAllByClubId(Long clubId) {
        var presences = repository.getPresenceEntityByClubId(clubId);
        return presences.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Presence getById(Long id) {
        Optional<PresenceEntity> entity = repository.getPresenceEntityById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Presence> getByUserId(UUID id) {
        List<PresenceEntity> presences = repository.getPresenceEntityByUserId(id);
        return presences.stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Presence> getByClubIdAndUserActiveAndDateEquals(Long clubId, Boolean active, LocalDate date) {
        List<PresenceEntity> presences = repository.getPresenceEntityByClubIdAndUserActiveAndDate(clubId, active, date);
        return presences.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<Presence> getByUserIdAndUserActiveAndDateEquals(UUID clubId, Boolean active, LocalDate date) {
        Optional<PresenceEntity> entity = repository.getPresenceEntityByUserIdAndUserActiveAndDate(clubId, active, date);
        return entity.map(mapper::toDomain);
    }

    @Override
    public Presence register(Presence presence) {
        PresenceEntity entity = mapper.toEntity(presence);
        return mapper.toDomain(repository.save(entity));
    }

}
