package br.com.portaldbv.infra.dto.speciality;

import br.com.portaldbv.domain.enums.SpecialityCategoryEnum;

public record SpecialityResponseDTO(

        Long id,
        String name,
        String year,
        SpecialityCategoryEnum category,
        String imageUrl

) {
}