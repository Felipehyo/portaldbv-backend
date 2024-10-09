package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Club;

import java.util.List;

public interface ClubRepositoryGateway {
    List<Club> getAll();

    Club getById(Long id);

    Club getByName(String name);

    Club register(Club club);

    Club update(Club club);

    void delete(Club club);
}
