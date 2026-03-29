package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.PresenceRepositoryGateway;
import br.com.portaldbv.domain.dto.PresencePercentageMetricsDTO;
import br.com.portaldbv.domain.entities.Kit;
import br.com.portaldbv.domain.entities.Presence;
import br.com.portaldbv.domain.entities.User;
import br.com.portaldbv.domain.enums.PresenceTypeEnum;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PresenceUseCases {

    private final PresenceRepositoryGateway repository;
    private final UserUseCases userUseCases;
    private final KitUseCases kitUseCases;

    public List<PresencePercentageMetricsDTO> getAllWithPercentage(Long clubId) {

        List<Presence> presences = repository.getAllByClubId(clubId);
        List<User> users = userUseCases.getAllByClub(clubId, null, Boolean.TRUE, Boolean.FALSE, null);

        var percents = new ArrayList<PresencePercentageMetricsDTO>();
        List<String> countDates = new ArrayList<>();

        presences.forEach(presence -> {
            if (!countDates.contains(presence.getDate().toString())) {
                countDates.add(presence.getDate().toString());
            }
        });

        if (!presences.isEmpty()) {
            users.forEach(user -> {
                AtomicInteger countPresences = new AtomicInteger();
                presences.forEach(presence -> {
                    if (user.getId().equals(presence.getUser().getId()) && PresenceTypeEnum.PRESENT.equals(presence.getPresenceType())) {
                        countPresences.getAndIncrement();
                    }
                });
                double percent = (100.00 / countDates.size()) * countPresences.get();
                percents.add(new PresencePercentageMetricsDTO(user, (int) percent));
            });
        }

        percents.sort(Comparator.comparing(PresencePercentageMetricsDTO::getUserName));

        return percents;
    }

    public List<Presence> getAllByDay(Long clubId, LocalDate date) {
        LocalDate targetDate = (date != null ? date : LocalDate.now(ZoneId.of("America/Sao_Paulo")));

        List<Presence> presences = repository.getByClubIdAndUserActiveAndDateEquals(clubId, Boolean.TRUE, targetDate);
        List<User> users = userUseCases.getAllByClub(clubId, null, Boolean.TRUE, Boolean.FALSE, null);

        Map<UUID, Presence> presencesByUserId = presences.stream()
                .filter(presence -> presence.getUser() != null && presence.getUser().getId() != null)
                .collect(Collectors.toMap(presence -> presence.getUser().getId(), Function.identity(), (first, second) -> first));

        return users.stream()
                .sorted(Comparator.comparing(User::getName, Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)))
                .map(user -> {
                    var oldPresence = presencesByUserId.get(user.getId());
                    if (oldPresence != null) {
                        return oldPresence;
                    }

                    return Presence.builder()
                            .user(user)
                            .club(user.getClub())
                            .date(targetDate)
                            .build();
                })
                .toList();
    }

    public List<Presence> getAllByClubIdOrUserId(Long clubId, UUID userId) {
        return userId != null ? repository.getByUserId(userId) : repository.getAllByClubId(clubId);
    }

    public Presence register(UUID userId, Presence presence, LocalDate date) {

        var user = userUseCases.getById(userId);

        Kit kit;
        presence.getKit().setUser(user);

        var oldPresence = repository.getByUserIdAndUserActiveAndDateEquals(userId, Boolean.TRUE, (date != null) ? date : LocalDate.now(ZoneId.of("America/Sao_Paulo")));

        if (oldPresence.isEmpty()) {
            if (presence.getPresenceType() == PresenceTypeEnum.PRESENT) {
                kit = kitUseCases.register(presence.getKit());
            } else {
                var kitRequest = new Kit();
                kitRequest.setUser(user);
                kit = kitUseCases.register(kitRequest);
                kit.setUser(user);
            }

            presence.setClub(user.getClub());
            presence.setUser(user);
            presence.setKit(kit);

            presence = repository.register(presence);
        } else {
            if (presence.getPresenceType() == PresenceTypeEnum.PRESENT) {
                kit = oldPresence.get().getKit();
                kit.setBible(presence.getKit().getBible());
                kit.setBibleStudy(presence.getKit().getBibleStudy());
                kit.setPencil(presence.getKit().getPencil());
                kit.setCap(presence.getKit().getCap());
                kit.setBottle(presence.getKit().getBottle());
                kit.setScarf(presence.getKit().getScarf());
                kit.setActivityNotebook(presence.getKit().getActivityNotebook());
                kit = kitUseCases.register(kit);
            } else {
                kit = oldPresence.get().getKit();
                kit.setBible(Boolean.FALSE);
                kit.setBibleStudy(Boolean.FALSE);
                kit.setPencil(Boolean.FALSE);
                kit.setCap(Boolean.FALSE);
                kit.setBottle(Boolean.FALSE);
                kit.setScarf(Boolean.FALSE);
                kit.setActivityNotebook(Boolean.FALSE);
                kit = kitUseCases.register(kit);
            }
            oldPresence.get().setKit(kit);
            oldPresence.get().setPresenceType(presence.getPresenceType());

            presence = repository.register(oldPresence.get());
        }

        return presence;
    }

}
