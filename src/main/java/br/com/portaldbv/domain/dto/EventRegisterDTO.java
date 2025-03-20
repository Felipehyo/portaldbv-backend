package br.com.portaldbv.domain.dto;

import br.com.portaldbv.domain.enums.GenderEnum;
import br.com.portaldbv.domain.enums.UserTypeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EventRegisterDTO {

    private UUID userId;
    private String userName;
    private UserTypeEnum userType;
    private GenderEnum userGender;
    private BigDecimal allocatedValue;
    private BigDecimal debtValue;
    private Double percentagePayment;

}
