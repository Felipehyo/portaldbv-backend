package br.com.portaldbv.infra.dto.request.unit;

import br.com.portaldbv.domain.enums.GenderEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UnitRequestDTO(

        @NotBlank(message = "O campo name deve ser informado!")
        String name,
        @NotNull(message = "O campo idade inicial deve ser informado!")
        Integer initialAge,
        @NotNull(message = "O campo idade final deve ser informado!")
        Integer finalAge,
        @NotNull(message = "O campo sexo deve ser informado!")
        GenderEnum gender,
        @NotNull(message = "O campo clubId deve ser informado!")
        Integer clubId,
        @NotNull(message = "O campo active deve ser informado!")
        Boolean active

) {
}