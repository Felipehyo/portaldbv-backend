package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.domain.enums.SpecialityCategoryEnum;
import br.com.portaldbv.infra.persistence.entities.SpecialityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<SpecialityEntity, Long> {
    Optional<SpecialityEntity> getSpecialityEntityById(Long id);

    Optional<SpecialityEntity> getSpecialityEntityByName(String name);

    List<SpecialityEntity> getSpecialityEntityByCategory(SpecialityCategoryEnum category);
}
