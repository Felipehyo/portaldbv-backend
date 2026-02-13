package br.com.portaldbv.infra.persistence.entities;

import br.com.portaldbv.domain.enums.MinutesTypeEnum;
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
@Table(name = "VIRTUAL_MINUTES",
        uniqueConstraints = @UniqueConstraint(columnNames = {"unit_id", "date", "type"}))
public class VirtualMinutesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MinutesTypeEnum type;

    @NotNull
    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Armazena até 3 URLs separadas por vírgula
    @Column(length = 1500)
    private String imageLinks;

    @ManyToMany
    @JoinTable(
            name = "VIRTUAL_MINUTES_PRESENT_USERS",
            joinColumns = @JoinColumn(name = "virtual_minutes_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private java.util.List<UserEntity> presentUsers;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private UnitEntity unit;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id")
    private UserEntity createdBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean active;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (active == null) {
            active = true;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
