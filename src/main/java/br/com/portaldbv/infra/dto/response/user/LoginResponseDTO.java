package br.com.portaldbv.infra.dto.response.user;

import java.util.UUID;

public record LoginResponseDTO(

        UUID userId,
        Long clubId
) {
}