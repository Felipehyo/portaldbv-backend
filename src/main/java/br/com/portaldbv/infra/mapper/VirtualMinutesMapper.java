package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.VirtualMinutes;
import br.com.portaldbv.infra.dto.virtualminutes.VirtualMinutesRequestDTO;
import br.com.portaldbv.infra.dto.virtualminutes.VirtualMinutesResponseDTO;
import br.com.portaldbv.infra.persistence.entities.VirtualMinutesEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VirtualMinutesMapper {

    @Mapping(target = "imageLinks", ignore = true)
    VirtualMinutesEntity toEntity(VirtualMinutes virtualMinutes);

    @Mapping(target = "imageLinks", ignore = true)
    VirtualMinutes toDomain(VirtualMinutesEntity virtualMinutesEntity);

    VirtualMinutes toDomain(VirtualMinutesRequestDTO virtualMinutesRequestDTO);

    @Mapping(source = "unit.id", target = "unitId")
    @Mapping(source = "unit.name", target = "unitName")
    @Mapping(source = "createdBy.id", target = "createdByUserId")
    @Mapping(source = "createdBy.name", target = "createdByUserName")
    VirtualMinutesResponseDTO toDTO(VirtualMinutes virtualMinutes);


    List<VirtualMinutesResponseDTO> toDTOList(List<VirtualMinutes> virtualMinutesList);
}

