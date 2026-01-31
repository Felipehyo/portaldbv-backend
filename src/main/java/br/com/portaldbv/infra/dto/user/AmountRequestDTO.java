package br.com.portaldbv.infra.dto.user;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AmountRequestDTO(
        @NotNull(message = "O valor deve ser informado!")
        BigDecimal amount) {
}
