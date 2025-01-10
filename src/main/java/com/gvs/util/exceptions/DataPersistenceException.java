package com.gvs.util.exceptions;

import com.gvs.util.constants;

public class DataPersistenceException extends BaseException {
    public DataPersistenceException(String message) {
        super(message, constants.errors.dataPersistenceError);
    }
}
