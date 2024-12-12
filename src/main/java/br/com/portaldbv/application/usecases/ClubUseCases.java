package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.ClubRepositoryGateway;
import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.domain.enums.error.ClubErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ClubUseCases {

    private final ClubRepositoryGateway repository;

    public ClubUseCases(ClubRepositoryGateway repository) {
        this.repository = repository;
    }

    public List<Club> getAll() {
        return repository.getAll();
    }

    public Club getById(Long id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(ClubErrorEnum.ID_NOT_FOUND));
    }

    public Club getByName(String name) {
        return Optional.ofNullable(repository.getByName(name))
                .orElseThrow(() -> new DomainException(ClubErrorEnum.NAME_NOT_FOUND));
    }

    public Club register(Club club) {

        if (repository.getByName(club.getName()) != null) {
            throw new DomainException(ClubErrorEnum.ALREADY_REGISTERED);
        }

        club.setBank(BigDecimal.ZERO);

        return repository.register(club);
    }

    public Club update(Long id, Club club) {
        var oldClub = getById(id);
        oldClub.setName(club.getName());
        return repository.update(oldClub);
    }

    public void deposit(Long id, BigDecimal value) {
        var club = getById(id);
        club.setBank(club.getBank().add(value));
        repository.update(club);
    }

    public void withdraw(Long id, BigDecimal value) {
        var club = getById(id);
        club.setBank(club.getBank().subtract(value));
        repository.update(club);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

}
