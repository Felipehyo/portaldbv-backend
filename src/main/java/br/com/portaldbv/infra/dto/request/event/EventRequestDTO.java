package br.com.portaldbv.infra.dto.request.event;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EventRequestDTO(
        @NotNull(message = "O nome deve ser informado!")
        String name,
        @NotNull(message = "O valor deve ser informado!")
        BigDecimal value,
        @NotNull(message = "A data deve ser informada!")
        LocalDate date) {
}
