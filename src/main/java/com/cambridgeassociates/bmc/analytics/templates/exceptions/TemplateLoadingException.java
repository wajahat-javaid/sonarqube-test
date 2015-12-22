package com.cambridgeassociates.bmc.analytics.templates.exceptions;

public class TemplateLoadingException extends RuntimeException {

    public TemplateLoadingException(String name, Throwable reason) {
        super(String.format("Template '%s' failed to load", name), reason);
    }

    public TemplateLoadingException(String name) {
        super(String.format("Template '%s' failed to load", name));
    }
}
