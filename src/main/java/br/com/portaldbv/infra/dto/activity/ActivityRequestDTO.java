package br.com.portaldbv.infra.dto.activity;

import lombok.Builder;

@Builder
public record ActivityRequestDTO(
        String name,
        String description,
        Integer merit,
        Integer demerit,
        Integer activityOrder,
        Boolean alwaysDisplay
) {
}