package br.com.portaldbv.infra.persistence.entities;


import br.com.portaldbv.domain.enums.BookTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CASH_BOOK")
public class CashBookEntity {

    @Id
    @UuidGenerator
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private ClubEntity club;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private EventEntity event;

    @NotNull
    private BookTypeEnum type;

    @NotNull
    private String description;

    private BigDecimal value;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "refund_id")
    private RefundEntity refund;

    private String proofImageUrl;

}