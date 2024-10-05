package br.com.portaldbv.domain.exceptions;

import br.com.portaldbv.domain.enums.error.ClubErrorEnum;
import lombok.Getter;

@Getter
public class ClubException extends RuntimeException {

    private final ClubErrorEnum error;

    public ClubException(ClubErrorEnum error) {
        this.error = error;
    }

}