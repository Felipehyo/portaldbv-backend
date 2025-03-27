package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.KitRepositoryGateway;
import br.com.portaldbv.domain.entities.Kit;
import br.com.portaldbv.infra.mapper.KitMapper;
import br.com.portaldbv.infra.persistence.entities.KitEntity;
import br.com.portaldbv.infra.persistence.repository.KitRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class KitRepositoryGatewayImpl implements KitRepositoryGateway {

    private final KitRepository repository;
    private final KitMapper mapper;

    @Override
    public Kit getById(Long id) {
        Optional<KitEntity> entity = repository.getKitEntityById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public List<Kit> getAllByUserId(UUID id) {
        List<KitEntity> kits = repository.getKitEntityByUserId(id);
        return kits.stream().map(mapper::toDomain).toList();
    }

    @Override
    public Kit save(Kit kit) {
        KitEntity entity = mapper.toEntity(kit);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public void delete(Kit kit) {
        repository.delete(mapper.toEntity(kit));
    }

}
