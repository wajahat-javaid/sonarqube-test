package com.cambridgeassociates.bmc.services;

import com.cambridgeassociates.bmc.analytics.templates.AnalyticTemplate;
import org.apache.ignite.Ignite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IgniteExecutionService {

    private Ignite ignite;

    @Autowired
    public IgniteExecutionService(Ignite ignite) {
        this.ignite = ignite;
    }

    // TODO consider reorganizing services
    public Map<String, Object> execute(AnalyticTemplate template, Map<String, Object> params) {
        return template.execute(ignite, params);
    }

    public Ignite getIgnite() {
        return ignite;
    }
}
