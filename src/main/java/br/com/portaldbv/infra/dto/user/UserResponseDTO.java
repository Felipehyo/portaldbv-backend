package br.com.portaldbv.infra.dto.user;

import br.com.portaldbv.domain.enums.GenderEnum;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import br.com.portaldbv.infra.dto.club.ClubResponseDTO;
import br.com.portaldbv.infra.persistence.entities.UnitEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public record UserResponseDTO(

        UUID id,
        String name,
        String cpf,
        String email,
//        String password,
        UserTypeEnum type,
        UnitEntity unit,
        Boolean active,
        Date birthDate,
        BigDecimal bank,
//        ClubResponseDTO club,
        GenderEnum gender

) {
}