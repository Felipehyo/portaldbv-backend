package br.com.portaldbv.infra.mapper;

import br.com.portaldbv.domain.entities.ActivityRecord;
import br.com.portaldbv.infra.dto.activity.record.ActivityRecordRequestDTO;
import br.com.portaldbv.infra.dto.activity.record.ActivityRecordResponseDTO;
import br.com.portaldbv.infra.persistence.entities.ActivityRecordEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityRecordMapper {

    ActivityRecordEntity toEntity(ActivityRecord activityRecord);

    ActivityRecord toDomain(ActivityRecordEntity entity);

    ActivityRecord toDomain(ActivityRecordRequestDTO request);

    ActivityRecordResponseDTO toDTO(ActivityRecord activityRecord);

    List<ActivityRecord> toDomainList(List<ActivityRecordEntity> cashBookEntities);

    List<ActivityRecordResponseDTO> toDTOList(List<ActivityRecord> activityRecords);

}