package com.country.routing.exception.handler;

import com.country.routing.exception.NoLandRouteFoundException;
import com.country.routing.exception.NoSuchCountryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RoutingHandler {
    @ExceptionHandler({NoSuchCountryException.class, NoLandRouteFoundException.class})
    public ResponseEntity<String> handleNoSuchCountryException(final Exception ex) {
        String errorMessage = ex.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(errorMessage, status);
    }
}
