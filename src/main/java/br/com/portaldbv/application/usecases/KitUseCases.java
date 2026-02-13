package br.com.portaldbv.application.usecases;

import br.com.portaldbv.application.gateways.KitRepositoryGateway;
import br.com.portaldbv.domain.dto.KitMetricsDTO;
import br.com.portaldbv.domain.entities.Kit;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.domain.enums.error.CashBookErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class KitUseCases {

    private final KitRepositoryGateway repository;
    private final UserUseCases userUseCases;

    public Kit getById(Long id) {
        return Optional.ofNullable(repository.getById(id))
                .orElseThrow(() -> new DomainException(CashBookErrorEnum.ID_NOT_FOUND));
    }

    public List<Kit> getAllByUserId(UUID userId) {
        return repository.getAllByUserId(userId);
    }

    public Kit register(UUID userId, Kit kit) {
        kit.setUser(userUseCases.getById(userId));
        return repository.save(kit);
    }

    public Kit register(Kit kit) {
        return repository.save(kit);
    }

    public KitMetricsDTO getMetricsByUser(UUID userId) {

        var user = userUseCases.getById(userId);

        var list = getAllByUserId(userId);

        var metrics = new KitMetricsDTO();

        list.forEach(kit -> countKitItems(kit, metrics));
        metrics.setUserId(userId);
        metrics.setUserName(user.getName());
        metrics.setTotal(metrics.sumTotal());

        return metrics;
    }

    public List<KitMetricsDTO> getAllMetricsByClub(Long clubId) {
        var users = userUseCases.getAllByClub(clubId, null, Boolean.TRUE, Boolean.FALSE, List.of(UserTypeEnum.PATHFINDER, UserTypeEnum.DIRECTION, UserTypeEnum.EXECUTIVE));

        var metrics = new ArrayList<KitMetricsDTO>();

        users.forEach(user -> {
            var presences = repository.getAllByUserId(user.getId());
            var metric = new KitMetricsDTO();
            presences.forEach(kit -> countKitItems(kit, metric));
            metric.setUserId(user.getId());
            metric.setUserName(user.getName());
            metric.setTotal(metric.sumTotal());
            metrics.add(metric);
        });

        return metrics;
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

    private void countKitItems(Kit kit, KitMetricsDTO metrics) {
        if (kit.getScarf()) metrics.setScarfQuantity(metrics.getScarfQuantity() + 1);
        if (kit.getBible()) metrics.setBibleQuantity(metrics.getBibleQuantity() + 1);
        if (kit.getActivityNotebook()) metrics.setActivityNotebookQuantity(metrics.getActivityNotebookQuantity() + 1);
        if (kit.getBottle()) metrics.setBottleQuantity(metrics.getBottleQuantity() + 1);
        if (kit.getCap()) metrics.setCapQuantity(metrics.getCapQuantity() + 1);
        if (kit.getPencil()) metrics.setPencilQuantity(metrics.getPencilQuantity() + 1);
        if (kit.getBibleStudy()) metrics.setBibleStudyQuantity(metrics.getBibleStudyQuantity() + 1);
    }


}
