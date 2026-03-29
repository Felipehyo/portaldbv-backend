package br.com.portaldbv.infra.dto.presence;

import br.com.portaldbv.domain.enums.PresenceTypeEnum;
import br.com.portaldbv.domain.entities.User;

public record PresenceTodayDTO(
        PresenceTypeEnum status,
        User user
) {
}

