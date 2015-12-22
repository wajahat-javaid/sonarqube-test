package com.cambridgeassociates.bmc.analytics.templates.exceptions;

public class TemplateNotRegisteredException extends RuntimeException {

    public TemplateNotRegisteredException(String name) {
        super(String.format("Template '%s' is not registered", name));
    }
}
