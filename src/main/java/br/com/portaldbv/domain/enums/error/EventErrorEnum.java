package br.com.portaldbv.domain.enums.error;

import br.com.portaldbv.domain.enums.constant.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventErrorEnum implements ErrorDomain {
    ALREADY_REGISTERED(400, Errors.EVENT_ALREADY_REGISTERED_MESSAGE),
    NOT_FOUND(404, Errors.EVENT_NOT_FOUND),
    ID_NOT_FOUND(404, Errors.EVENT_ID_NOT_FOUND),
    INVALID_CLUB(400, Errors.EVENT_INVALID_CLUB),
    NAME_NOT_FOUND(404, Errors.EVENT_NAME_NOT_FOUND);

    private final Integer httpStatusCode;
    private final String details;

}