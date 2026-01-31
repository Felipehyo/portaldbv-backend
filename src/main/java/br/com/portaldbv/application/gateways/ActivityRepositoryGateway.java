package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityRepositoryGateway {

    Activity getById(Long id);
    Optional<Activity> getByName(String name);

    List<Activity> getAllByClubId(Long clubId);

    Activity save(Activity activity);

    void delete(Activity activity);

}
