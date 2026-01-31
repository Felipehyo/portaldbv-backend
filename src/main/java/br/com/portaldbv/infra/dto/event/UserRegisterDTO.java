package br.com.portaldbv.infra.dto.event;

import br.com.portaldbv.domain.enums.EventTypeRegisterEnum;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserRegisterDTO(
        @NotNull(message = "O id do usuário deve ser informado!")
        UUID userId,
        @NotNull(message = "Deve ser informado")
        EventTypeRegisterEnum registerType
) {
}
