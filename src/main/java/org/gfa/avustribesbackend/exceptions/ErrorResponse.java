package org.gfa.avustribesbackend.exceptions;

import org.springframework.http.HttpStatus;
import java.util.Date;

public class ErrorResponse {

    private String message;
    private String endpoint;
    private HttpStatus httpStatus;
    private Date time;

    public ErrorResponse(String message, String endpoint, HttpStatus httpStatus, Date time) {
        this.message = message;
        this.endpoint = endpoint;
        this.httpStatus = httpStatus;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Date getTime() {
        return time;
    }
}
