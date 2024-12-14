package br.com.portaldbv.infra.dto.response.unit;

import br.com.portaldbv.domain.enums.GenderEnum;

public record UnitResponseDTO(

        Long id,
        String name,
        Integer initialAge,
        Integer finalAge,
        GenderEnum gender,
        Integer clubId,
        String imageLink,
        Integer qtdPoints,
        Integer deliveryPendingPoints,
        Boolean active

) {
}