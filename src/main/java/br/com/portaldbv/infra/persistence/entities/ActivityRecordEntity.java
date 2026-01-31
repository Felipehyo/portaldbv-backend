package br.com.portaldbv.infra.persistence.entities;

import br.com.portaldbv.domain.enums.RecordTypeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACTIVITY_RECORD")
public class ActivityRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "unit_id")
    private UnitEntity unit;

    @OneToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activity;

    @NotNull
    private RecordTypeEnum type;

    private String title;
    private String reason;
    private LocalDateTime createdDate;
    private LocalDate date;
    private Integer points = 0;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity registeredByUser;

}