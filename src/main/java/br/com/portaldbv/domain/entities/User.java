package br.com.portaldbv.domain.entities;

import br.com.portaldbv.domain.enums.GenderEnum;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private UUID id;
    private String name;
    private String cpf;
    private String email;
    private String password;
    private UserTypeEnum userType;
    private Unit unit;
    private Boolean active;
    private Date birthDate;
    private BigDecimal bank;
    private Club club;
    private GenderEnum gender;

}