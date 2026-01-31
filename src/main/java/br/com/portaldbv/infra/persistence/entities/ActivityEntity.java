package br.com.portaldbv.infra.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACTIVITY")
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Integer merit;

    @NotNull
    private Integer demerit;
    private Integer activityOrder;
    private Boolean alwaysDisplay;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private ClubEntity club;

}