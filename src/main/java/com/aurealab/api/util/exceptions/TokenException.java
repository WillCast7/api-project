package com.aurealab.api.util.exceptions;

import com.aurealab.api.util.constants;

public class TokenException extends BaseException {
    public TokenException(String message, Throwable cause) {
        super(message, constants.errors.tokenCreationError, cause);
    }
}