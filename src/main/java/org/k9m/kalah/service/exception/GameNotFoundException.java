package org.k9m.kalah.service.exception;

import org.k9m.kalah.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class GameNotFoundException extends ApplicationException {

    public GameNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
