package br.com.portaldbv.infra.dto.request.refund;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record RefundRequestDTO(

        Long clubId,
        UUID userId,
        Long eventId,
        BigDecimal value,
        Boolean alreadyRefunded,
        LocalDate refundedDate,
        LocalDateTime createdDate
) {
}
