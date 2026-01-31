package br.com.portaldbv.domain.dto;

import br.com.portaldbv.domain.entities.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityPointsMetricsDTO {

    private Unit unit;
    private Integer total;

}
