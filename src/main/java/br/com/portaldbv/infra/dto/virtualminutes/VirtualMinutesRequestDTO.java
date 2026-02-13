package br.com.portaldbv.infra.dto.virtualminutes;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record VirtualMinutesRequestDTO(
        @NotNull(message = "A data deve ser informada!")
        LocalDate date,
        @NotNull(message = "A descrição deve ser informada!")
        String description,
        // Lista opcional de usuários presentes (apenas para ata de secretaria)
        List<UUID> presentUserIds
) {
}
