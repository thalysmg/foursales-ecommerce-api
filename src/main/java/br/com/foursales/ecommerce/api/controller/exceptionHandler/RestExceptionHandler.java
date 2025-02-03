package br.com.foursales.ecommerce.api.controller.exceptionHandler;

import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class RestExceptionHandler {

    @ResponseStatus(value = FORBIDDEN)
    @ExceptionHandler(value = AuthenticationException.class)
    public ProblemDetail forbiddenException(AuthenticationException e) {
        return ProblemDetail.forStatusAndDetail(UNAUTHORIZED, e.getMessage());
    }
}
