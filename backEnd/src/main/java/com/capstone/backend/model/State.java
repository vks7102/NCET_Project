package com.capstone.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "states")
public class State {
    @Id
    private String id;
    private String stateCode;
    private String stateName;
    private String stateType;

    public State() {}

    public State(String stateCode, String stateName, String stateType) {
        this.stateCode = stateCode;
        this.stateName = stateName;
        this.stateType = stateType;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }
    public String getStateName() { return stateName; }
    public void setStateName(String stateName) { this.stateName = stateName; }
    public String getStateType() { return stateType; }
    public void setStateType(String stateType) { this.stateType = stateType; }
}
