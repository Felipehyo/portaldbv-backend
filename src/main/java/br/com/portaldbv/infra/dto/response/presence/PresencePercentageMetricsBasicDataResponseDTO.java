package br.com.portaldbv.infra.dto.response.presence;

import br.com.portaldbv.infra.dto.response.user.UserBasicDataResponseDTO;

public record PresencePercentageMetricsBasicDataResponseDTO(
        UserBasicDataResponseDTO user,
        Integer percentage
) {
}