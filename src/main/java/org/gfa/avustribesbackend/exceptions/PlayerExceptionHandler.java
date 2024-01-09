package org.gfa.avustribesbackend.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class PlayerExceptionHandler {

  @ExceptionHandler({
    CredentialException.class,
    VerificationException.class,
    CreationException.class,
  })
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponse badRequestExceptionHandler(
      RuntimeException runtimeException, HttpServletRequest httpServletRequest) {
    return new ErrorResponse(
        runtimeException.getMessage(),
        httpServletRequest.getRequestURI(),
        new Date(System.currentTimeMillis()));
  }

  @ExceptionHandler({AlreadyExistsException.class})
  @ResponseStatus(value = HttpStatus.CONFLICT)
  public ErrorResponse conflictExceptionHandler(
      RuntimeException runtimeException, HttpServletRequest httpServletRequest) {
    return new ErrorResponse(
        runtimeException.getMessage(),
        httpServletRequest.getRequestURI(),
        new Date(System.currentTimeMillis()));
  }
}
