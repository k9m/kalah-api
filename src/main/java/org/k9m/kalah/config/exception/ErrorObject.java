package org.k9m.kalah.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorObject {

    private final String message;
    private final HttpStatus statusCode;

    public ErrorObject(final ApplicationException applicationException){
        this.message = applicationException.getMessage();
        this.statusCode = applicationException.getStatusCode();
    }

    public ErrorObject(final String message, final HttpStatus statusCode){
        this.message = message;
        this.statusCode = statusCode;
    }
}
