package br.com.portaldbv.infra.dto.activity;

import lombok.Builder;

@Builder
public record ActivityResponseDTO(
        Long id,
        String name,
        String description,
        Integer merit,
        Integer demerit,
        Integer activityOrder,
        Boolean alwaysDisplay
) {
}