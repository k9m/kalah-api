package org.k9m.kalah.service.game.exception;

import org.k9m.kalah.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class InvalidPitExceptionException extends ApplicationException {

    public InvalidPitExceptionException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
