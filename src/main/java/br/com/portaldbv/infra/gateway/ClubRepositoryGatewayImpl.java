package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.ClubRepositoryGateway;
import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.infra.mapper.ClubMapper;
import br.com.portaldbv.infra.persistence.entities.ClubEntity;
import br.com.portaldbv.infra.persistence.repository.ClubRepository;

import java.util.List;
import java.util.Optional;

public class ClubRepositoryGatewayImpl implements ClubRepositoryGateway {

    private final ClubRepository clubRepository;
    private final ClubMapper mapper;

    public ClubRepositoryGatewayImpl(ClubRepository clubRepository, ClubMapper mapper) {
        this.clubRepository = clubRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Club> getAll() {
        List<ClubEntity> clubs = clubRepository.findAll();
        return mapper.toDomainList(clubs);
    }

    @Override
    public Club getById(Long id) {
        Optional<ClubEntity> entity = clubRepository.getClubEntityById(id);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public Club getByName(String name) {
        Optional<ClubEntity> entity = clubRepository.getClubEntityByName(name);
        return entity.map(mapper::toDomain).orElse(null);
    }

    @Override
    public Club register(Club club) {
        ClubEntity entity = mapper.toEntity(club);
        return mapper.toDomain(clubRepository.save(entity));
    }

    @Override
    public Club update(Club club) {
        return mapper.toDomain(clubRepository.save(mapper.toEntity(club)));
    }

    @Override
    public void delete(Club club) {
        clubRepository.delete(mapper.toEntity(club));
    }
}
