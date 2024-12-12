package br.com.portaldbv.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityRequirement {

    private Long specialityId;
    private Integer order;
    private String description;

}
