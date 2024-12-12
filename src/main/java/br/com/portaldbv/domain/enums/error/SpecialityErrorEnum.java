package br.com.portaldbv.domain.enums.error;

import br.com.portaldbv.domain.enums.constant.Errors;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SpecialityErrorEnum implements ErrorDomain {
    ALREADY_REGISTERED(400, Errors.SPECIALITY_ALREADY_REGISTERED_MESSAGE),
    ID_NOT_FOUND(404, Errors.SPECIALITY_ID_NOT_FOUND),
    INVALID_CATEGORY(400, Errors.INVALID_CATEGORY);

    private final Integer httpStatusCode;
    private final String details;

}