package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.Activity;
import br.com.portaldbv.infra.dto.activity.ActivityRequestDTO;
import br.com.portaldbv.infra.dto.activity.ActivityResponseDTO;
import br.com.portaldbv.infra.persistence.entities.ActivityEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    ActivityEntity toEntity(Activity cashBook);

    Activity toDomain(ActivityEntity cashBookEntity);

    Activity toDomain(ActivityRequestDTO cashBookRequestDTO);

    ActivityResponseDTO toDTO(Activity cashBook);

    List<Activity> toDomainList(List<ActivityEntity> cashBookEntities);

    List<ActivityResponseDTO> toDTOList(List<Activity> cashBooks);
}