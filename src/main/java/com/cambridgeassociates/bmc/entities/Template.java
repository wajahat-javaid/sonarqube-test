package com.cambridgeassociates.bmc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "TEMPLATES")
public class Template {

    @Id
    @Column(name = "TEMPLATE_NAME")
    private String name;

    @Column(name = "CLOSURE_CLASS")
    private String closureClass;

    @Column(name = "JAR_FILE")
    private String jarPath;

    public Template() {
    }

    public Template(String name, String closureClass, String jarPath) {
        this.name = name;
        this.closureClass = closureClass;
        this.jarPath = jarPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClosureClass() {
        return closureClass;
    }

    public void setClosureClass(String closureClass) {
        this.closureClass = closureClass;
    }

    public String getJarPath() {
        return jarPath;
    }

    public void setJarPath(String jarPath) {
        this.jarPath = jarPath;
    }
}
