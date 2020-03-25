package org.k9m.kalah.api.exception;

import org.k9m.kalah.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class WrongPlayerTurnException extends ApplicationException {

    public WrongPlayerTurnException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
