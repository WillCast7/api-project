package com.aurealab.api.util.exceptions;

import com.aurealab.api.util.constants;

public class DataPersistenceException extends BaseException {
    public DataPersistenceException(String message) {
        super(message, constants.errors.dataPersistenceError);
    }
}
