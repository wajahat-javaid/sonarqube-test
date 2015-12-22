package com.cambridgeassociates.bmc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "TEMPLATE_EXECUTION")
public class Execution {

    @Id
    @Column(name = "TEMPLATE_ID")
    private String id;

    @Column(name = "TEMPLATE_NAME")
    private String name;

    @Column(name = "EXECUTION_PARAMS")
    private String params;

    @Column(name = "EXECUTION_RESULT")
    private String results;

    @Column(name = "EXECUTION_METRICS")
    private String metrics;

    public Execution() {

    }

    public Execution(String id, String name, String params, String results, String metrics) {
        super();
        this.id = id;
        this.name = name;
        this.params = params;
        this.results = results;
        this.metrics = metrics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    public String getMetrics() {
        return metrics;
    }

    public void setMetrics(String metrics) {
        this.metrics = metrics;
    }

}
