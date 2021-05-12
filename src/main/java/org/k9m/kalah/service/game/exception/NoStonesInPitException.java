package org.k9m.kalah.service.game.exception;

import org.k9m.kalah.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NoStonesInPitException extends ApplicationException {

    public NoStonesInPitException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
