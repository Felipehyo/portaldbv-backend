package br.com.portaldbv.infra.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClubRequestDTO(

        @NotBlank(message = "O campo name deve ser informado!")
        String name

) {
}