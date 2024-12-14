package br.com.portaldbv.domain.entities;

import br.com.portaldbv.domain.enums.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Unit {

    private Long id;
    private String name;
    private String imageLink;
    private Integer initialAge;
    private Integer finalAge;
    private GenderEnum gender;
    private Long clubId;
    private Integer qtdPoints;
    private Integer deliveryPendingPoints;
    private Boolean active;

}