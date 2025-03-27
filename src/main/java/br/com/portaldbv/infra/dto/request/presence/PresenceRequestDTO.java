package br.com.portaldbv.infra.dto.request.presence;

import br.com.portaldbv.domain.enums.PresenceTypeEnum;
import br.com.portaldbv.infra.dto.request.kit.KitRequestDTO;

import java.time.LocalDate;

public record PresenceRequestDTO(
        LocalDate date,
        PresenceTypeEnum presenceType,
        KitRequestDTO kit
) {
}