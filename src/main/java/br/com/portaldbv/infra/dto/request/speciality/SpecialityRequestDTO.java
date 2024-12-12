package br.com.portaldbv.infra.dto.request.speciality;

import jakarta.validation.constraints.NotBlank;

public record SpecialityRequestDTO(

        @NotBlank(message = "O campo name deve ser informado!")
        String name,
        String year,
        String category

) {
}