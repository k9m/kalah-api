package org.k9m.kalah.api.exception;

import org.k9m.kalah.config.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class GameHistoryNotFoundException extends ApplicationException {

    public GameHistoryNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
