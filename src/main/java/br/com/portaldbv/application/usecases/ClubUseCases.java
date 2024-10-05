package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.ClubRepositoryGateway;
import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.domain.enums.error.ClubErrorEnum;
import br.com.portaldbv.domain.exceptions.ClubException;

import java.math.BigDecimal;
import java.util.Optional;

public class ClubUseCases {

    private final ClubRepositoryGateway repository;

    public ClubUseCases(ClubRepositoryGateway repository) {
        this.repository = repository;
    }

    public Club getById(Long id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new ClubException(ClubErrorEnum.ID_NOT_FOUND));
    }

    public Club getByName(String name) {
        return Optional.ofNullable(repository.getByName(name))
                .orElseThrow(() -> new ClubException(ClubErrorEnum.NAME_NOT_FOUND));
    }

    public Club register(Club club) {

        if (repository.getByName(club.getName()) != null) {
            throw new ClubException(ClubErrorEnum.ALREADY_REGISTERED);
        }

        club.setBank(BigDecimal.ZERO);

        return repository.register(club);
    }

}
