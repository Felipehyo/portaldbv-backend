package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.Kit;
import br.com.portaldbv.infra.dto.kit.KitRequestDTO;
import br.com.portaldbv.infra.dto.kit.KitResponseDTO;
import br.com.portaldbv.infra.persistence.entities.KitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface KitMapper {

    KitEntity toEntity(Kit kit);

    Kit toDomain(KitEntity kitEntity);

    Kit toDomain(KitRequestDTO kitRequestDTO);

    @Mapping(source = "user.id", target = "userId")
    KitResponseDTO toResponse(Kit kit);

    @Mapping(source = "user.id", target = "userId")
    List<KitResponseDTO> toResponseList(List<Kit> kits);

    List<Kit> toDomainList(List<KitEntity> kitEntities);
}
