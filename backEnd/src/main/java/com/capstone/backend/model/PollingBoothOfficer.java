package com.capstone.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "polling_booth_officers")
public class PollingBoothOfficer {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String assignmentType;
    private String booth;
    private String mobilityBooth;
    private String state;
    private String district;
    private String assembly;
    private String constituency;
    private Boolean isAssigned = false;
    private String ero;
    private Date createdAt;
    private Date updatedAt;

    public PollingBoothOfficer() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getAssignmentType() { return assignmentType; }
    public void setAssignmentType(String assignmentType) { this.assignmentType = assignmentType; }
    public String getBooth() { return booth; }
    public void setBooth(String booth) { this.booth = booth; }
    public String getMobilityBooth() { return mobilityBooth; }
    public void setMobilityBooth(String mobilityBooth) { this.mobilityBooth = mobilityBooth; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getAssembly() { return assembly; }
    public void setAssembly(String assembly) { this.assembly = assembly; }
    public String getConstituency() { return constituency; }
    public void setConstituency(String constituency) { this.constituency = constituency; }
    public Boolean getIsAssigned() { return isAssigned; }
    public void setIsAssigned(Boolean isAssigned) { this.isAssigned = isAssigned; }
    public String getEro() { return ero; }
    public void setEro(String ero) { this.ero = ero; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
