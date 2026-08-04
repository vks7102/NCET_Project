package com.capstone.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "pcs")
public class Pcs {
    @Id
    private String id;
    private String stateCode;
    private String pcCode;
    private String pcName;

    public Pcs() {}

    public Pcs(String stateCode, String pcCode, String pcName) {
        this.stateCode = stateCode;
        this.pcCode = pcCode;
        this.pcName = pcName;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }
    public String getPcCode() { return pcCode; }
    public void setPcCode(String pcCode) { this.pcCode = pcCode; }
    public String getPcName() { return pcName; }
    public void setPcName(String pcName) { this.pcName = pcName; }
}
