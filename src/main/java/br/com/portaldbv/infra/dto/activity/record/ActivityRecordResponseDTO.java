package br.com.portaldbv.infra.dto.activity.record;

import br.com.portaldbv.domain.enums.RecordTypeEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record ActivityRecordResponseDTO(
        Long id,
        String unitName,
        String activityName,
        RecordTypeEnum type,
        String title,
        String reason,
        LocalDateTime createdDate,
        LocalDate date,
        Integer points
) {
}