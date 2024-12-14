package br.com.portaldbv.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GenderEnum {

    FEMALE("Feminino"),
    MALE("Masculino");

    private final String className;

}
