package br.com.portaldbv.infra.persistence.entities;

import br.com.portaldbv.domain.enums.PresenceTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRESENCE")
public class PresenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "kit_id")
    private KitEntity kit;

    private LocalDate date;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private PresenceTypeEnum presenceType;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private ClubEntity club;

}