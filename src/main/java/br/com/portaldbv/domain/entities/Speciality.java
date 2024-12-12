package br.com.portaldbv.domain.entities;

import br.com.portaldbv.domain.enums.SpecialityCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Speciality {

    private String id;
    private String name;
    private String year;
    private SpecialityCategoryEnum category;
    private String imageUrl;
//    private ArrayList<SpecialityRequirement> specialityRequirements;

}
