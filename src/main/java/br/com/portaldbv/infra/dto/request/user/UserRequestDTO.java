package br.com.portaldbv.infra.dto.request.user;

import br.com.portaldbv.domain.enums.GenderEnum;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record UserRequestDTO(

        @NotBlank(message = "O campo name deve ser informado!")
        String name,
        String cpf,
        String email,
        String password,
        @NotNull(message = "O campo tipo de usuário deve ser informado!")
        UserTypeEnum userType,
        Long unitId,
        Date birthDate,
        @NotNull(message = "O campo sexo deve ser informado!")
        GenderEnum gender

) {
}