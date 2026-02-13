package br.com.portaldbv.domain.enums.error;

import br.com.portaldbv.domain.enums.constant.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VirtualMinutesErrorEnum implements ErrorDomain {
    ALREADY_REGISTERED(400, Errors.VIRTUAL_MINUTES_ALREADY_REGISTERED_MESSAGE),
    NOT_FOUND(404, Errors.VIRTUAL_MINUTES_NOT_FOUND),
    ID_NOT_FOUND(404, Errors.VIRTUAL_MINUTES_ID_NOT_FOUND),
    INVALID_UNIT(400, Errors.VIRTUAL_MINUTES_INVALID_UNIT),
    INVALID_USER(400, Errors.VIRTUAL_MINUTES_INVALID_USER),
    MAX_IMAGES_EXCEEDED(400, Errors.VIRTUAL_MINUTES_MAX_IMAGES_EXCEEDED);

    private final Integer httpStatusCode;
    private final String details;

}

