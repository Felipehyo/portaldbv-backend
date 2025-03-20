package br.com.portaldbv.infra.dto.request.user;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @NotBlank(message = "O campo name deve ser informado!")
        String email,
        @NotBlank(message = "O campo name deve ser informado!")
        String password

) {
}