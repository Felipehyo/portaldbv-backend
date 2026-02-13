package br.com.portaldbv.infra.persistence.repository;

import br.com.portaldbv.domain.enums.MinutesTypeEnum;
import br.com.portaldbv.infra.persistence.entities.VirtualMinutesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VirtualMinutesRepository extends JpaRepository<VirtualMinutesEntity, Long> {

    Optional<VirtualMinutesEntity> getVirtualMinutesEntityById(Long id);

    // Buscar atas de uma unidade em uma data específica
    @Query("SELECT v FROM VirtualMinutesEntity v WHERE v.unit.id = :unitId " +
            "AND v.date = :date " +
            "ORDER BY v.type")
    List<VirtualMinutesEntity> getVirtualMinutesEntityByUnitIdAndDate(
            @Param("unitId") Long unitId,
            @Param("date") LocalDate date
    );

    // Verificar se já existe ata de um tipo específico para uma unidade em uma data
    @Query("SELECT v FROM VirtualMinutesEntity v WHERE v.unit.id = :unitId " +
            "AND v.date = :date " +
            "AND v.type = :type")
    Optional<VirtualMinutesEntity> findByUnitIdAndDateAndType(
            @Param("unitId") Long unitId,
            @Param("date") LocalDate date,
            @Param("type") MinutesTypeEnum type
    );

    // Buscar todas as atas de uma unidade
    @Query("SELECT v FROM VirtualMinutesEntity v WHERE v.unit.id = :unitId " +
            "AND (:onlyActives = FALSE OR v.active = TRUE) " +
            "ORDER BY v.date DESC, v.type")
    List<VirtualMinutesEntity> getVirtualMinutesEntityByUnitIdAndFilters(
            @Param("unitId") Long unitId,
            @Param("onlyActives") Boolean onlyActives
    );

    // Buscar atas por período
    @Query("SELECT v FROM VirtualMinutesEntity v WHERE v.unit.id = :unitId " +
            "AND v.date BETWEEN :initialDate AND :finalDate " +
            "ORDER BY v.date DESC, v.type")
    List<VirtualMinutesEntity> getVirtualMinutesEntityByUnitIdAndDateBetween(
            @Param("unitId") Long unitId,
            @Param("initialDate") LocalDate initialDate,
            @Param("finalDate") LocalDate finalDate
    );
}

