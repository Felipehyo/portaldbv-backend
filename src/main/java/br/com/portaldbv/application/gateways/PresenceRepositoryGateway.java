package br.com.portaldbv.application.gateways;

import br.com.portaldbv.domain.entities.Presence;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PresenceRepositoryGateway {

    List<Presence> getAllByClubId(Long clubId);

    Presence getById(Long id);

    List<Presence> getByUserId(UUID id);

    List<Presence> getByClubIdAndUserActiveAndDateEquals(Long clubId, Boolean active, LocalDate date);

    Optional<Presence> getByUserIdAndUserActiveAndDateEquals(UUID clubId, Boolean active, LocalDate date);

    Presence register(Presence presence);
}
