package br.com.portaldbv.domain.dto;

import br.com.portaldbv.domain.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEventRegisterDTO {

    private String userName;
    private UUID userId;
    private GenderEnum userGender;
    private Boolean subscribe;

}