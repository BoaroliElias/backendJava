package br.cervejariaSC.expedbeer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class IncorrectPasswordException extends RuntimeException {

    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(final String message) {
        super(message);
    }

    public IncorrectPasswordException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
