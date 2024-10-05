package br.com.portaldbv.infra.dto.response;

import java.math.BigDecimal;

public record ClubResponseDTO(
        Long id, String name, BigDecimal bank

) {
}