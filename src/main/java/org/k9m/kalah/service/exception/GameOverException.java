package org.k9m.kalah.service.exception;

import org.k9m.kalah.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class GameOverException extends ApplicationException {

    public GameOverException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
