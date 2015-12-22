package com.cambridgeassociates.bmc.controllers.dto;

import java.util.Map;

public class ExecutionResponse {

    private String name;
    private String uniqueId;
    private String requestedBy;
    private Map<String, Object> result;
    private Map<String, Object> metrics;

    public ExecutionResponse() {
    }

    public ExecutionResponse(String name, String uniqueId, String requestedBy, Map<String, Object> result, Map<String, Object> metrics) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.requestedBy = requestedBy;
        this.result = result;
        this.metrics = metrics;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Map<String, Object> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Object> metrics) {
        this.metrics = metrics;
    }
}
