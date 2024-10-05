package br.com.portaldbv.infra.handler;

import br.com.portaldbv.domain.exceptions.ClubException;
import br.com.portaldbv.infra.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ClubAdvice {

    @ExceptionHandler(value = {ClubException.class})
    ResponseEntity<Object> clubExceptionHandler(ClubException exception) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getError().getHttpStatusCode())).body(new ErrorDTO(exception.getError().name(), exception.getError().getDetails()));
    }

}
