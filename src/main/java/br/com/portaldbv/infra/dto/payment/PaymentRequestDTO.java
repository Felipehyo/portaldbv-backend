package br.com.portaldbv.infra.dto.payment;

import br.com.portaldbv.domain.enums.FormOfPaymentEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentRequestDTO(
        BigDecimal value,
        FormOfPaymentEnum formOfPayment,
        LocalDate date,
        Long clubId,
        Long eventId,
        UUID userId
) {
}
