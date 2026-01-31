package br.com.portaldbv.infra.dto.event;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EventResponseDTO(
        Long id,
        String name,
        BigDecimal value,
        LocalDate date,
        BigDecimal bank,
        Boolean active,
        Integer subscriptionQuantity // todo fazer
) {
}
