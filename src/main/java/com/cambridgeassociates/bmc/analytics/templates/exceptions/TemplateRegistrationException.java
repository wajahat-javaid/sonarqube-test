package com.cambridgeassociates.bmc.analytics.templates.exceptions;

public class TemplateRegistrationException extends RuntimeException {

    public TemplateRegistrationException(String name, Throwable reason) {
        super(String.format("Template '%s' failed to register", name), reason);
    }

    public TemplateRegistrationException(String name) {
        super(String.format("Template '%s' failed to register", name));
    }
}
