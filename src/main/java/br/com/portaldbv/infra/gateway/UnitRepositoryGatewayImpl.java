package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.UnitRepositoryGateway;
import br.com.portaldbv.domain.entities.Unit;
import br.com.portaldbv.infra.mapper.ClubMapper;
import br.com.portaldbv.infra.mapper.UnitMapper;
import br.com.portaldbv.infra.persistence.entities.UnitEntity;
import br.com.portaldbv.infra.persistence.repository.UnitRepository;

import java.util.List;
import java.util.Optional;

public class UnitRepositoryGatewayImpl implements UnitRepositoryGateway {

    private final UnitRepository unitRepository;
    private final UnitMapper mapper;

    public UnitRepositoryGatewayImpl(UnitRepository unitRepository, UnitMapper mapper) {
        this.unitRepository = unitRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Unit> getAllByClubId(Long clubId) {
        List<UnitEntity> units = unitRepository.getUnitEntityByClubId(clubId);
        return mapper.toDomainList(units);
    }

    @Override
    public Unit getById(Long id) {
        Optional<UnitEntity> entity = unitRepository.getUnitEntityById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public Unit getByClubIdAndName(Long clubId, String name) {
        Optional<UnitEntity> entity = unitRepository.getUnitEntityByNameAndClubId(name, clubId);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public Unit register(Unit unit) {
        UnitEntity entity = mapper.toEntity(unit);
        return mapper.toDomain(unitRepository.save(entity));
    }

    @Override
    public Unit update(Unit unit) {
        return mapper.toDomain(unitRepository.save(mapper.toEntity(unit)));
    }

    @Override
    public void delete(Unit unit) {
        unitRepository.delete(mapper.toEntity(unit));
    }
}
