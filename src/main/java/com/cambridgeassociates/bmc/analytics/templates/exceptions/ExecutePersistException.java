package com.cambridgeassociates.bmc.analytics.templates.exceptions;

public class ExecutePersistException extends RuntimeException {

    public ExecutePersistException(String name, Throwable reason) {
        super(String.format("Unable to save data for Execution: '%s'", name), reason);
    }

    public ExecutePersistException(String name) {
        super(String.format("Unable to save data for Execution: '%s'", name));
    }
}
