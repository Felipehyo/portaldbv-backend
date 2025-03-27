package br.com.portaldbv.infra.dto.response.user;

import java.util.UUID;

public record UserBasicDataResponseDTO(

        UUID id,
        String name

) {
}