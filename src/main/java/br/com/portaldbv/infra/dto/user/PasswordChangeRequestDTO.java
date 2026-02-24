package br.com.portaldbv.infra.dto.user;

import jakarta.validation.constraints.NotBlank;

public record PasswordChangeRequestDTO(
        @NotBlank(message = "A senha atual deve ser informada")
        String currentPassword,
        @NotBlank(message = "A nova senha deve ser informada")
        String newPassword
) {
}

