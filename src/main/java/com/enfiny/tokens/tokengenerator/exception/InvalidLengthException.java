package com.enfiny.tokens.tokengenerator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidLengthException extends RuntimeException {

    public InvalidLengthException(String message) {
        super(message);
    }
}
