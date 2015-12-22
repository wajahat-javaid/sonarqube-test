package com.cambridgeassociates.bmc.controllers.dto;

import java.util.Map;

public class ExecutionRequest {

    private String name;
    private String uniqueId;
    private String requestedBy;
    private Map<String, Object> params;

    public ExecutionRequest() {
    }

    public ExecutionRequest(String name, String uniqueId, String requestedBy, Map<String, Object> params) {
        this.name = name;
        this.uniqueId = uniqueId;
        this.requestedBy = requestedBy;
        this.params = params;
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

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}
