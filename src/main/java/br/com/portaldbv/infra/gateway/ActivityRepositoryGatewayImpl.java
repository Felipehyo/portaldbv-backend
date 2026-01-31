package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.ActivityRepositoryGateway;
import br.com.portaldbv.domain.entities.Activity;
import br.com.portaldbv.infra.mapper.ActivityMapper;
import br.com.portaldbv.infra.persistence.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ActivityRepositoryGatewayImpl implements ActivityRepositoryGateway {

    private final ActivityRepository repository;
    private final ActivityMapper mapper;

    @Override
    public Activity getById(Long id) {
        return repository
                .findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Atividade com ID " + id + " não encontrado."));
    }

    @Override
    public Optional<Activity> getByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Activity> getAllByClubId(Long clubId) {
        return repository
                .findByClubId(clubId)
                .stream().map(mapper::toDomain).toList();
    }

    @Override
    public Activity save(Activity request) {
        return mapper.toDomain(repository.save(mapper.toEntity(request)));
    }

    @Override
    public void delete(Activity request) {
        repository.delete(mapper.toEntity(request));
    }
}
