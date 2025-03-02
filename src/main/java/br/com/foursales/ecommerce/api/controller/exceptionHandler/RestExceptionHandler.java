package br.com.foursales.ecommerce.api.controller.exceptionHandler;

import br.com.foursales.ecommerce.api.exceptions.RegistroNaoEncontradoException;
import br.com.foursales.ecommerce.api.exceptions.RegraDeNegocioException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static java.util.stream.Collectors.joining;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ProblemDetail handleGeneralException(Exception e) {
        log.error(e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ProblemDetail handleAuthenticationException(AuthenticationException e) {
        log.error(e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream()
            .map(error -> {
                if (error instanceof FieldError fieldError) {
                    return fieldError.getField() + " " + fieldError.getDefaultMessage();
                } else {
                    return error.getDefaultMessage();
                }
            }).collect(joining(", "));
        log.error(message, e);
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, message);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ProblemDetail handleConstraintViolationException(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
            .map(cv -> cv.getPropertyPath() + " " +  cv.getMessage()).collect(joining(", "));
        log.error(message, e);
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, message);
    }

    @ExceptionHandler(value = RegraDeNegocioException.class)
    public ProblemDetail handleRegraDeNegocioException(RegraDeNegocioException e) {
        log.error(e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler(value = RegistroNaoEncontradoException.class)
    public ProblemDetail handleRegistroNaoEncontrado(RegistroNaoEncontradoException e) {
        log.error(e.getMessage(), e);
        return ProblemDetail.forStatusAndDetail(NOT_FOUND, e.getMessage());
    }
}
