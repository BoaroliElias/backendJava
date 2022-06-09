package br.cervejariaSC.expedbeer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class LoginAlreadyExistsException extends RuntimeException {

    public LoginAlreadyExistsException() {
        super();
    }

    public LoginAlreadyExistsException(final String message) {
        super(message);
    }

    public LoginAlreadyExistsException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
