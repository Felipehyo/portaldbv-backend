package br.com.portaldbv.domain.enums.error;

import br.com.portaldbv.domain.enums.constant.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActivityErrorEnum implements ErrorDomain {
    ALREADY_REGISTERED(400, Errors.ALREADY_REGISTERED),
    NOT_EXIST(400, Errors.UNIT_NOT_EXIST),
    ID_NOT_FOUND(404, Errors.ID_NOT_FOUND),;

    private final Integer httpStatusCode;
    private final String details;

}