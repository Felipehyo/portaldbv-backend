package br.com.portaldbv.domain.enums;

import br.com.portaldbv.domain.enums.error.SpecialityErrorEnum;
import br.com.portaldbv.domain.exceptions.DomainException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpecialityCategoryEnum {

    ADRA("ADRA"),
    ARTS_AND_CRAFTS("Artes e Habilidades Manuais"),
    AGRICULTURAL_ACTIVITIES("Atividades Agrícolas"),
    PROFESSIONAL_ACTIVITIES("Atividades Profissionais"),
    RECREATIONAL_ACTIVITIES("Atividades Recreativas"),
    SCIENCE_AND_HEALTH("Ciência e Saúde"),
    NATURE_STUDIES("Estudos da Natureza"),
    HOUSEKEEPING_SKILLS("Habilidades Domésticas"),
    MASTERS_DEGREE("Mestrado");

    private final String name;

    public static SpecialityCategoryEnum getByName(String name) {
        for (var category : SpecialityCategoryEnum.values()) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        throw new DomainException(SpecialityErrorEnum.INVALID_CATEGORY);
    }
}
