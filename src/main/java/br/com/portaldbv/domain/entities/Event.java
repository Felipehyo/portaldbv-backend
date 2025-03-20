package br.com.portaldbv.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private Long id;
    private String name;
    private BigDecimal value;
    private LocalDate date;
    private Club club;
    private BigDecimal bank;
    private Boolean active;

}