package br.com.portaldbv.infra.dto.cashbook;

import br.com.portaldbv.domain.entities.Refund;
import br.com.portaldbv.domain.enums.BookTypeEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CashBookRequestDTO(
        UUID id,
        Integer eventId,
        Refund refund,
        BookTypeEnum type,
        String description,
        BigDecimal value,
        LocalDate date
) {
}