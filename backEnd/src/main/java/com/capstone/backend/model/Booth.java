package com.capstone.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booths")
public class Booth {
    @Id
    private String id;
    private String acCode;
    private String boothNo;
    private String boothName;

    public Booth() {}

    public Booth(String acCode, String boothNo, String boothName) {
        this.acCode = acCode;
        this.boothNo = boothNo;
        this.boothName = boothName;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAcCode() { return acCode; }
    public void setAcCode(String acCode) { this.acCode = acCode; }
    public String getBoothNo() { return boothNo; }
    public void setBoothNo(String boothNo) { this.boothNo = boothNo; }
    public String getBoothName() { return boothName; }
    public void setBoothName(String boothName) { this.boothName = boothName; }
}
