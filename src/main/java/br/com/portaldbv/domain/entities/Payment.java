package br.com.portaldbv.domain.entities;

import br.com.portaldbv.domain.enums.FormOfPaymentEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private UUID id;
    private Club club;
    private User user;
    private Event event;
    private BigDecimal value;
    private FormOfPaymentEnum formOfPayment;
    private LocalDate date;
    private LocalDateTime createAt;

}