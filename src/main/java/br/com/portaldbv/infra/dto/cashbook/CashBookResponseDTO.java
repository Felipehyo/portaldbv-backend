package br.com.portaldbv.infra.dto.cashbook;

import br.com.portaldbv.domain.entities.Event;
import br.com.portaldbv.domain.entities.Refund;
import br.com.portaldbv.domain.enums.BookTypeEnum;
import br.com.portaldbv.infra.dto.event.EventResponseDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CashBookResponseDTO(
        UUID id,
        EventResponseDTO event,
        Refund refund,
        BookTypeEnum type,
        String description,
        BigDecimal value,
        LocalDate date
) {
}