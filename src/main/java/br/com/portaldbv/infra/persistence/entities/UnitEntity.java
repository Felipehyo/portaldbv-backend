package br.com.portaldbv.infra.persistence.entities;

import br.com.portaldbv.domain.entities.Club;
import br.com.portaldbv.domain.enums.GenderEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UNIT")
public class UnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    private Integer qtdPoints;
    private String imageLink;
    private Integer initialAge;
    private Integer finalAge;
    private GenderEnum gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", insertable = false, updatable = false)
    private ClubEntity club;

    @Column(name = "club_id")
    private Long clubId;

    private Integer deliveryPendingPoints;
    private Boolean active;

}