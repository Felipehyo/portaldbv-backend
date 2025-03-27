package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Kit;

import java.util.List;
import java.util.UUID;

public interface KitRepositoryGateway {

    Kit getById(Long id);

    List<Kit> getAllByUserId(UUID id);

    Kit save(Kit kit);

    void delete(Kit kit);

}
