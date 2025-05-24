package br.com.portaldbv.domain.entities;

import br.com.portaldbv.infra.persistence.entities.ClubEntity;
import br.com.portaldbv.infra.persistence.entities.EventEntity;
import br.com.portaldbv.infra.persistence.entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Refund {

    private Long id;
    private Club club;
    private User user;
    private Event event;
    private BigDecimal value;
    private Boolean alreadyRefunded;
    private LocalDate refundedDate;
    private LocalDateTime createdDate;
}