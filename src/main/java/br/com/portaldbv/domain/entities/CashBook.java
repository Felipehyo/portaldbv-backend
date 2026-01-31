package br.com.portaldbv.domain.entities;


import br.com.portaldbv.domain.enums.BookTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CashBook {

    private UUID id;
    private Club club;
    private Event event;
    private Refund refund;
    private BookTypeEnum type;
    private String description;
    private BigDecimal value;
    private LocalDate date;

}