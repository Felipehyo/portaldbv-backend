package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Unit;

import java.util.List;

public interface UnitRepositoryGateway {

    List<Unit> getAllByClubId(Long clubId);

    Unit getById(Long id);

    Unit getByClubIdAndName(Long clubId, String name);

    Unit register(Unit unit);

    Unit update(Unit unit);

    void delete(Unit unit);

}
