package com.gvs.util.exceptions;

import com.gvs.util.constants;

public class TokenException extends BaseException {
    public TokenException(String message, Throwable cause) {
        super(message, constants.errors.tokenCreationError, cause);
    }
}