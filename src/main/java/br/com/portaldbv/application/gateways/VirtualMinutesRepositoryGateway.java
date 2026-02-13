package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.VirtualMinutes;
import br.com.portaldbv.domain.enums.MinutesTypeEnum;

import java.time.LocalDate;
import java.util.List;

public interface VirtualMinutesRepositoryGateway {

    List<VirtualMinutes> getAllByUnitIdAndFilters(Long unitId, Boolean onlyActives);

    VirtualMinutes getById(Long id);

    List<VirtualMinutes> getByUnitIdAndDate(Long unitId, LocalDate date);

    List<VirtualMinutes> getByUnitIdAndDateBetween(Long unitId, LocalDate initialDate, LocalDate finalDate);

    VirtualMinutes findByUnitIdAndDateAndType(Long unitId, LocalDate date, MinutesTypeEnum type);

    VirtualMinutes register(VirtualMinutes virtualMinutes);

    VirtualMinutes update(VirtualMinutes virtualMinutes);

    void delete(VirtualMinutes virtualMinutes);

}

