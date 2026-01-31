package br.com.portaldbv.infra.dto.presence;

import br.com.portaldbv.domain.enums.PresenceTypeEnum;
import br.com.portaldbv.infra.dto.kit.KitBasicDataResponseDTO;
import br.com.portaldbv.infra.dto.user.UserBasicDataResponseDTO;

import java.time.LocalDate;

public record PresenceResponseDTO(
        Long id,
        PresenceTypeEnum presenceType,
        UserBasicDataResponseDTO user,
        KitBasicDataResponseDTO kit,
        LocalDate date

) {
}