package br.com.portaldbv.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    private Long id;
    private String name;
    private String description;
    private Integer merit;
    private Integer demerit;
    private Integer activityOrder;
    private Boolean alwaysDisplay;
    private Club club;

}