package br.com.portaldbv.infra.dto.response.kit;

public record KitBasicDataResponseDTO(

        Long id,
        Boolean scarf,
        Boolean bible,
        Boolean activityNotebook,
        Boolean bottle,
        Boolean cap,
        Boolean pencil,
        Boolean bibleStudy
) {
}