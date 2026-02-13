package br.com.portaldbv.infra.dto.virtualminutes;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VirtualMinutesRequestDTO(
        @NotNull(message = "A data deve ser informada!")
        LocalDate date,
        @NotNull(message = "A descrição deve ser informada!")
        String description
) {
}

