package br.com.portaldbv.infra.dto.request.club;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record BankRequestDTO(

        @NotNull(message = "O campo value deve ser informado!")
        BigDecimal value

) {
}