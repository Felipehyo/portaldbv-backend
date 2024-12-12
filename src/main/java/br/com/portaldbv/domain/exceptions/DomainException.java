package br.com.portaldbv.domain.exceptions;

import br.com.portaldbv.domain.enums.error.ErrorDomain;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final ErrorDomain error;

    public DomainException(ErrorDomain error) {
        this.error = error;
    }

}