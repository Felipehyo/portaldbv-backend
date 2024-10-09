package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.infra.persistence.entities.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Long> {
    Optional<ClubEntity> getClubEntityById(Long id);
    Optional<ClubEntity> getClubEntityByName(String name);
}
