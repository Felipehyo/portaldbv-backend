package br.com.portaldbv.infra.dto.user;

import br.com.portaldbv.domain.enums.UserTypeEnum;

import java.util.UUID;

public record LoginResponseDTO(

        UUID userId,
        UserTypeEnum type,
        Long clubId
) {
}