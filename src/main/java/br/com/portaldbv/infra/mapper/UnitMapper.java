package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.Unit;
import br.com.portaldbv.infra.dto.request.unit.UnitRequestDTO;
import br.com.portaldbv.infra.dto.response.unit.UnitResponseDTO;
import br.com.portaldbv.infra.persistence.entities.UnitEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnitMapper {

    UnitEntity toEntity(Unit unit);

    Unit toDomain(UnitEntity unitEntity);

    Unit toDomain(UnitRequestDTO unitRequestDTO);

    UnitResponseDTO toResponse(Unit unit);

    List<Unit> toDomainList(List<UnitEntity> unitEntities);

    List<UnitResponseDTO> toReponseList(List<Unit> units);
}
