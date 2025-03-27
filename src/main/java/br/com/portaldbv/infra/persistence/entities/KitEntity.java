package br.com.portaldbv.infra.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KIT")
public class KitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private UserEntity user;

    private Boolean scarf;
    private Boolean bible;
    private Boolean activityNotebook;
    private Boolean bottle;
    private Boolean cap;
    private Boolean pencil;
    private Boolean bibleStudy;

}