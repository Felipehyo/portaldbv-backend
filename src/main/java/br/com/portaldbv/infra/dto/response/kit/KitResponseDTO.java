package br.com.portaldbv.infra.dto.response.kit;

import java.util.UUID;

public record KitResponseDTO(

        Long id,
        UUID userId,
        Boolean scarf,
        Boolean bible,
        Boolean activityNotebook,
        Boolean bottle,
        Boolean cap,
        Boolean pencil,
        Boolean bibleStudy
) {
}