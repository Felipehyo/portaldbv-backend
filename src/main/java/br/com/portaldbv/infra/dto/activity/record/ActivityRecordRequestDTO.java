package br.com.portaldbv.infra.dto.activity.record;

import br.com.portaldbv.domain.enums.RecordTypeEnum;
import lombok.Builder;

@Builder
public record ActivityRecordRequestDTO(
        String title,
        String reason,
        RecordTypeEnum type,
        Integer points
) {
}