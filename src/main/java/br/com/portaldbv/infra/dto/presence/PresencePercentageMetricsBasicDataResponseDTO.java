package br.com.portaldbv.infra.dto.presence;

import br.com.portaldbv.infra.dto.user.UserBasicDataResponseDTO;

public record PresencePercentageMetricsBasicDataResponseDTO(
        UserBasicDataResponseDTO user,
        Integer percentage
) {
}