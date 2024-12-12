package br.com.portaldbv.infra.handler;

import br.com.portaldbv.domain.exceptions.DomainException;
import br.com.portaldbv.infra.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerAdvice {

    @ExceptionHandler(value = {DomainException.class})
    ResponseEntity<Object> clubExceptionHandler(DomainException exception) {
        return ResponseEntity.status(HttpStatus.valueOf(exception.getError().getHttpStatusCode())).body(new ErrorDTO(exception.getError().name(), exception.getError().getDetails()));
    }

}
