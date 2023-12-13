package org.gfa.avustribesbackend.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class PlayerExceptionHandler {

    @ExceptionHandler({UserNameException.class,
            PasswordException.class,
            EmailException.class,
            VerificationException.class,
            NewPlayerCreationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestExceptionHandler(RuntimeException runtimeException,
                                                    HttpServletRequest httpServletRequest) {
        return new ErrorResponse(
                runtimeException.getMessage(),
                httpServletRequest.getContextPath(),
                HttpStatus.BAD_REQUEST,
                new Date(System.currentTimeMillis())
        );
    }

    @ExceptionHandler({UserNameAlreadyExistsException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorResponse conflictExceptionHandler(RuntimeException runtimeException,
                                                  HttpServletRequest httpServletRequest) {
        return new ErrorResponse(
                runtimeException.getMessage(),
                httpServletRequest.getContextPath(),
                HttpStatus.CONFLICT,
                new Date(System.currentTimeMillis())
        );
    }
}
