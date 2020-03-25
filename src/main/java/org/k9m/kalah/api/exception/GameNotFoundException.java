package org.k9m.kalah.api.exception;

import org.k9m.kalah.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class GameNotFoundException extends ApplicationException {

    public GameNotFoundException(HttpStatus statusCode, String message) {
        super(statusCode, message);
    }
}
