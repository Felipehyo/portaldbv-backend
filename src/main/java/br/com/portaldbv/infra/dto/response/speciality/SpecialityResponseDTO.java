package br.com.portaldbv.infra.dto.response.speciality;

import br.com.portaldbv.domain.enums.SpecialityCategoryEnum;

public record SpecialityResponseDTO(

        Long id,
        String name,
        String year,
        SpecialityCategoryEnum category,
        String imageUrl

) {
}