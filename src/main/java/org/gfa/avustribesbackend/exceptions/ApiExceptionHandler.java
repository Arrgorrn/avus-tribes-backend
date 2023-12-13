package org.gfa.avustribesbackend.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({UserNameException.class})
    public ResponseEntity<ErrorResponse> userNameExceptionHandler(UserNameException userNameException,
                                                                  WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                userNameException.getMessage(),
                webRequest.getDescription(false).substring(4),
                new Date(System.currentTimeMillis())
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
