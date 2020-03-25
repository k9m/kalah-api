package org.k9m.kalah.config.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    @Getter
    private HttpStatus statusCode;

    public ApplicationException(final HttpStatus statusCode, final String message){
        super(message);
        this.statusCode = statusCode;
    }

}
