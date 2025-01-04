package br.com.portaldbv.domain.enums.error;

import br.com.portaldbv.domain.enums.constant.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserErrorEnum implements ErrorDomain {
    ALREADY_REGISTERED(400, Errors.USER_ALREADY_REGISTERED),
    ID_NOT_FOUND(404, Errors.USER_ID_NOT_FOUND),
    INVALID_USER(404, Errors.INVALID_USER),
    INVALID_PASSWORD(404, Errors.INVALID_PASSWORD);

    private final Integer httpStatusCode;
    private final String details;

}