package br.com.portaldbv.infra.dto.presence;

import br.com.portaldbv.domain.enums.PresenceTypeEnum;
import br.com.portaldbv.infra.dto.kit.KitRequestDTO;

import java.time.LocalDate;

public record PresenceRequestDTO(
        LocalDate date,
        PresenceTypeEnum presenceType,
        KitRequestDTO kit
) {
}