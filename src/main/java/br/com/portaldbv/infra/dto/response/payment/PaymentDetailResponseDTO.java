package br.com.portaldbv.infra.dto.response.payment;

import br.com.portaldbv.domain.enums.FormOfPaymentEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PaymentDetailResponseDTO(
        UUID id,
        UserPaymentDTO user,
        EventPaymentDTO event,
        BigDecimal value,
        FormOfPaymentEnum formOfPayment,
        LocalDate date
//        LocalDateTime createAt
) {

    public record UserPaymentDTO(
            String name
    ) {
    }

    public record EventPaymentDTO(
            String name
    ) {
    }

}
