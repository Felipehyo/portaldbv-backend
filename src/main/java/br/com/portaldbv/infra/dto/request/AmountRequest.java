package br.com.portaldbv.infra.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AmountRequest(
        @NotNull(message = "O valor deve ser informado!")
        BigDecimal amount) {
}
