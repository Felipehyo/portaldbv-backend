package br.com.portaldbv.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BookTypeEnum {

    INPUT("Entrada"),
    OUTPUT("Saída");

    private final String value;

}