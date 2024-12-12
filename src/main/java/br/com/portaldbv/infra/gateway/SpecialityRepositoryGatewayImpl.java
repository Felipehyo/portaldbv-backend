package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.SpecialityRepositoryGateway;
import br.com.portaldbv.domain.entities.Speciality;
import br.com.portaldbv.domain.enums.SpecialityCategoryEnum;
import br.com.portaldbv.infra.mapper.SpecialityMapper;
import br.com.portaldbv.infra.persistence.entities.SpecialityEntity;
import br.com.portaldbv.infra.persistence.repository.SpecialityRepository;

import java.util.List;
import java.util.Optional;

public class SpecialityRepositoryGatewayImpl implements SpecialityRepositoryGateway {

    private final SpecialityRepository repository;
    private final SpecialityMapper mapper;

    public SpecialityRepositoryGatewayImpl(SpecialityRepository repository, SpecialityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Speciality> getAll() {
        List<SpecialityEntity> specialityEntities = repository.findAll();
        return mapper.toDomainList(specialityEntities);
    }

    @Override
    public Speciality getById(Long id) {
        Optional<SpecialityEntity> entity = repository.getSpecialityEntityById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public Speciality getByName(String name) {
        Optional<SpecialityEntity> entity = repository.getSpecialityEntityByName(name);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Speciality> getByCategory(SpecialityCategoryEnum specialityCategory) {
        List<SpecialityEntity> specialityEntities = repository.getSpecialityEntityByCategory(specialityCategory);
        return mapper.toDomainList(specialityEntities);
    }

    @Override
    public Speciality register(Speciality speciality) {
        SpecialityEntity entity = mapper.toEntity(speciality);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Speciality update(Speciality speciality) {
        return mapper.toDomain(repository.save(mapper.toEntity(speciality)));
    }

    @Override
    public void delete(Speciality speciality) {
        repository.delete(mapper.toEntity(speciality));
    }

}
