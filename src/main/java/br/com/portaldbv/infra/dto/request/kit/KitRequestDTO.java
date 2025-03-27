package br.com.portaldbv.infra.dto.request.kit;

public record KitRequestDTO(
        Boolean scarf,
        Boolean bible,
        Boolean activityNotebook,
        Boolean bottle,
        Boolean cap,
        Boolean pencil,
        Boolean bibleStudy
) {
}