package br.com.portaldbv.infra.dto.user;

import java.util.UUID;

public record UserBasicDataResponseDTO(

        UUID id,
        String name

) {
}