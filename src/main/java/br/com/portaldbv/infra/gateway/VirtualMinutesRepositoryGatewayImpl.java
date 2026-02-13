package br.com.portaldbv.infra.gateway;

import br.com.portaldbv.application.gateways.VirtualMinutesRepositoryGateway;
import br.com.portaldbv.domain.entities.VirtualMinutes;
import br.com.portaldbv.domain.enums.MinutesTypeEnum;
import br.com.portaldbv.infra.mapper.UserMapper;
import br.com.portaldbv.infra.mapper.VirtualMinutesMapper;
import br.com.portaldbv.infra.persistence.entities.VirtualMinutesEntity;
import br.com.portaldbv.infra.persistence.repository.VirtualMinutesRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class VirtualMinutesRepositoryGatewayImpl implements VirtualMinutesRepositoryGateway {

    private final VirtualMinutesRepository virtualMinutesRepository;
    private final VirtualMinutesMapper mapper;
    private final UserMapper userMapper;

    @Override
    public List<VirtualMinutes> getAllByUnitIdAndFilters(Long unitId, Boolean onlyActives) {
        List<VirtualMinutesEntity> entities = virtualMinutesRepository.getVirtualMinutesEntityByUnitIdAndFilters(unitId, onlyActives);
        return entities.stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public VirtualMinutes getById(Long id) {
        Optional<VirtualMinutesEntity> entity = virtualMinutesRepository.getVirtualMinutesEntityById(id);
        return entity.map(this::entityToDomain).orElse(null);
    }

    @Override
    public List<VirtualMinutes> getByUnitIdAndDate(Long unitId, LocalDate date) {
        List<VirtualMinutesEntity> entities = virtualMinutesRepository.getVirtualMinutesEntityByUnitIdAndDate(unitId, date);
        return entities.stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<VirtualMinutes> getByUnitIdAndDateBetween(Long unitId, LocalDate initialDate, LocalDate finalDate) {
        List<VirtualMinutesEntity> entities = virtualMinutesRepository.getVirtualMinutesEntityByUnitIdAndDateBetween(unitId, initialDate, finalDate);
        return entities.stream()
                .map(this::entityToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public VirtualMinutes findByUnitIdAndDateAndType(Long unitId, LocalDate date, MinutesTypeEnum type) {
        Optional<VirtualMinutesEntity> entity = virtualMinutesRepository.findByUnitIdAndDateAndType(unitId, date, type);
        return entity.map(this::entityToDomain).orElse(null);
    }

    @Override
    public VirtualMinutes register(VirtualMinutes virtualMinutes) {
        VirtualMinutesEntity entity = domainToEntity(virtualMinutes);
        return entityToDomain(virtualMinutesRepository.save(entity));
    }

    @Override
    public VirtualMinutes update(VirtualMinutes virtualMinutes) {
        return entityToDomain(virtualMinutesRepository.save(domainToEntity(virtualMinutes)));
    }

    @Override
    public void delete(VirtualMinutes virtualMinutes) {
        virtualMinutesRepository.delete(domainToEntity(virtualMinutes));
    }

    // Helper methods para converter imageLinks
    private VirtualMinutes entityToDomain(VirtualMinutesEntity entity) {
        VirtualMinutes domain = mapper.toDomain(entity);
        if (entity.getImageLinks() != null && !entity.getImageLinks().isEmpty()) {
            domain.setImageLinks(Arrays.asList(entity.getImageLinks().split(",")));
        }
        // converter presentUsers usando UserMapper
        if (entity.getPresentUsers() != null && !entity.getPresentUsers().isEmpty()) {
            domain.setPresentUsers(entity.getPresentUsers().stream().map(userMapper::toDomain).collect(Collectors.toList()));
        }
        return domain;
    }

    private VirtualMinutesEntity domainToEntity(VirtualMinutes domain) {
        VirtualMinutesEntity entity = mapper.toEntity(domain);
        if (domain.getImageLinks() != null && !domain.getImageLinks().isEmpty()) {
            entity.setImageLinks(String.join(",", domain.getImageLinks()));
        }
        // converter presentUsers usando UserMapper
        if (domain.getPresentUsers() != null && !domain.getPresentUsers().isEmpty()) {
            entity.setPresentUsers(domain.getPresentUsers().stream().map(userMapper::toEntity).collect(Collectors.toList()));
        }
        return entity;
    }

}
