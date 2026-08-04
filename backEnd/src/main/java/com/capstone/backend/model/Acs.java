package com.capstone.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "acs")
public class Acs {
    @Id
    private String id;
    private String stateCode;
    private String pcCode;
    private String assemblyCode;
    private String assemblyName;

    public Acs() {}

    public Acs(String stateCode, String pcCode, String assemblyCode, String assemblyName) {
        this.stateCode = stateCode;
        this.pcCode = pcCode;
        this.assemblyCode = assemblyCode;
        this.assemblyName = assemblyName;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }
    public String getPcCode() { return pcCode; }
    public void setPcCode(String pcCode) { this.pcCode = pcCode; }
    public String getAssemblyCode() { return assemblyCode; }
    public void setAssemblyCode(String assemblyCode) { this.assemblyCode = assemblyCode; }
    public String getAssemblyName() { return assemblyName; }
    public void setAssemblyName(String assemblyName) { this.assemblyName = assemblyName; }
}
