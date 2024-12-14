package br.com.portaldbv.domain.enums.error;

import br.com.portaldbv.domain.enums.constant.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnitErrorEnum implements ErrorDomain {
    ALREADY_REGISTERED(400, Errors.UNIT_ALREADY_REGISTERED_MESSAGE),
    ID_NOT_FOUND(404, Errors.UNIT_ID_NOT_FOUND),
    NAME_NOT_FOUND(404, Errors.UNIT_NAME_NOT_FOUND);

    private final Integer httpStatusCode;
    private final String details;

}