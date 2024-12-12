package br.com.portaldbv.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClassEnum {

    FRIEND("Amigo"),
    COMPANION("Companheiro"),
    EXPLORER("Pesquisador"),
    RANGER("Pioneiro"),
    VOYAGER("Excursionista"),
    GUIDE("Guia"),
    MASTER_GUIDE("Lider"),
    ADVANCED_MASTER_GUIDE("Lider Master");

    private final String className;

}
