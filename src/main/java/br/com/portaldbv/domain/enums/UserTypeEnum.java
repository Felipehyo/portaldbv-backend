package br.com.portaldbv.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    EXECUTIVE("EXECUTIVE"),
    DIRECTION("DIRECTION"),
    PATHFINDER("PATHFINDER"),
    EVENTUAL("EVENTUAL");

    public final String description;

}