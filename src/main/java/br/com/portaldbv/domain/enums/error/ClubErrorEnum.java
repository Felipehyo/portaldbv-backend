package br.com.portaldbv.domain.enums.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClubErrorEnum implements ErrorDomain {
    ALREADY_REGISTERED(400, Errors.CLUB_ALREADY_REGISTERED_MESSAGE),
    ID_NOT_FOUND(404, Errors.CLUB_ID_NOT_FOUND),
    NAME_NOT_FOUND(404, Errors.CLUB_NAME_NOT_FOUND);

    private final Integer httpStatusCode;
    private final String details;

}