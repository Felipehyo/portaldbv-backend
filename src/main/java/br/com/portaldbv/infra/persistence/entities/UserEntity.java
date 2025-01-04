package br.com.portaldbv.infra.persistence.entities;

import br.com.portaldbv.domain.enums.GenderEnum;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @UuidGenerator
    private UUID id;

    @Column(name = "name", length = 130, nullable = false)
    private String name;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "password", length = 16)
    private String password;

    @Column(name = "user_type", length = 12, nullable = false)
    private UserTypeEnum userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private UnitEntity unit;

    private Boolean active;

    @Temporal(TemporalType.DATE)
    @Column(name = "birth_date")
    private Date birthDate;

    private BigDecimal bank;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private ClubEntity club;

    @Column(name = "gender", length = 12)
    private GenderEnum gender;

}