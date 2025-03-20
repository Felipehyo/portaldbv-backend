package br.com.portaldbv.domain.enums.error;

import br.com.portaldbv.domain.enums.constant.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentErrorEnum implements ErrorDomain {
    ALREADY_REGISTERED(400, Errors.EVENT_ALREADY_REGISTERED_MESSAGE),
    NOT_FOUND(404, Errors.PAYMENT_NOT_FOUND),
    ID_NOT_FOUND(404, Errors.PAYMENT_ID_NOT_FOUND),
    INSUFFICIENT_BALANCE(404, Errors.PAYMENT_INSUFFICIENT_BALANCE);

    private final Integer httpStatusCode;
    private final String details;

}