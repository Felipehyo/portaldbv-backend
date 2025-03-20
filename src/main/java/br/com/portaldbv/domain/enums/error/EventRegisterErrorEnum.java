package br.com.portaldbv.domain.enums.error;

import br.com.portaldbv.domain.enums.constant.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventRegisterErrorEnum implements ErrorDomain {
    ALREADY_REGISTERED(400, Errors.EVENT_REGISTER_ALREADY_REGISTERED),
    NO_REGISTERED(400, Errors.EVENT_REGISTER_NOT_REGISTERED),
    NOT_FOUND(404, Errors.PAYMENT_NOT_FOUND),
    ID_NOT_FOUND(404, Errors.PAYMENT_ID_NOT_FOUND),
    INVALID_CLUB(400, Errors.PAYMENT_INVALID_CLUB),
    NAME_NOT_FOUND(404, Errors.PAYMENT_NAME_NOT_FOUND);

    private final Integer httpStatusCode;
    private final String details;

}