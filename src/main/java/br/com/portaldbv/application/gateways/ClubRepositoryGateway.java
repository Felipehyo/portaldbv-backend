package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Club;

public interface ClubRepositoryGateway {
    Club getById(Long id);

    Club getByName(String name);

    Club register(Club club);
}
