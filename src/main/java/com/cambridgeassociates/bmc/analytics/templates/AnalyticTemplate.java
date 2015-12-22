package com.cambridgeassociates.bmc.analytics.templates;

import org.apache.ignite.Ignite;

import java.util.Map;

public interface AnalyticTemplate {

    void before(Ignite ignite);

    Map<String, Object> execute(Ignite ignite, Map<String, Object> params);

    void after(Ignite ignite);
}
