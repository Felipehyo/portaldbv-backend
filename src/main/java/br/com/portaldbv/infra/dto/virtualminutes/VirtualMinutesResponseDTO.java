package br.com.portaldbv.infra.dto.virtualminutes;

import br.com.portaldbv.domain.enums.MinutesTypeEnum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record VirtualMinutesResponseDTO(
        Long id,
        MinutesTypeEnum type,
        LocalDate date,
        String description,
        List<String> imageLinks,
        Long unitId,
        String unitName,
        UUID createdByUserId,
        String createdByUserName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean active,
        // Nomes dos usuários presentes (formatados)
        List<String> presentUserNames
) {
}
