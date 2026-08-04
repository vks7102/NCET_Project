package com.capstone.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "voters")
public class Voter {
    @Id
    private String id;
    private String state;
    private String district;
    private String assembley;
    private String boothNumber;
    private String consituency;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String password;
    private Relative relative;
    private String phoneNumber;
    private String email;
    private String aadharNumber;
    private String gender;
    private Date dob;
    private Address address;
    private Disability disability;
    private String uniqueVoterId;
    private String referenceId;
    private String mobilityBoothId;
    private Boolean isVerifiedMobilityBoothId = false;
    private Boolean isDeleted = false;
    private Date deletedAt;
    private String deletionReason;
    private String deletedBy;

    public static class Relative {
        private String type;
        private String name;
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public static class Address {
        private String houseNumber;
        private String village;
        private String tehsil;
        private String postOffice;
        private String policeStation;
        private String district;
        private String state;
        private String pincode;
        public String getHouseNumber() { return houseNumber; }
        public void setHouseNumber(String houseNumber) { this.houseNumber = houseNumber; }
        public String getVillage() { return village; }
        public void setVillage(String village) { this.village = village; }
        public String getTehsil() { return tehsil; }
        public void setTehsil(String tehsil) { this.tehsil = tehsil; }
        public String getPostOffice() { return postOffice; }
        public void setPostOffice(String postOffice) { this.postOffice = postOffice; }
        public String getPoliceStation() { return policeStation; }
        public void setPoliceStation(String policeStation) { this.policeStation = policeStation; }
        public String getDistrict() { return district; }
        public void setDistrict(String district) { this.district = district; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getPincode() { return pincode; }
        public void setPincode(String pincode) { this.pincode = pincode; }
    }

    public static class Disability {
        private String type;
        private String certificate;
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public String getCertificate() { return certificate; }
        public void setCertificate(String certificate) { this.certificate = certificate; }
    }

    public Voter() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getAssembley() { return assembley; }
    public void setAssembley(String assembley) { this.assembley = assembley; }
    public String getBoothNumber() { return boothNumber; }
    public void setBoothNumber(String boothNumber) { this.boothNumber = boothNumber; }
    public String getConsituency() { return consituency; }
    public void setConsituency(String consituency) { this.consituency = consituency; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Relative getRelative() { return relative; }
    public void setRelative(Relative relative) { this.relative = relative; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAadharNumber() { return aadharNumber; }
    public void setAadharNumber(String aadharNumber) { this.aadharNumber = aadharNumber; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public Date getDob() { return dob; }
    public void setDob(Date dob) { this.dob = dob; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }
    public Disability getDisability() { return disability; }
    public void setDisability(Disability disability) { this.disability = disability; }
    public String getUniqueVoterId() { return uniqueVoterId; }
    public void setUniqueVoterId(String uniqueVoterId) { this.uniqueVoterId = uniqueVoterId; }
    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String referenceId) { this.referenceId = referenceId; }
    public String getMobilityBoothId() { return mobilityBoothId; }
    public void setMobilityBoothId(String mobilityBoothId) { this.mobilityBoothId = mobilityBoothId; }
    public Boolean getIsVerifiedMobilityBoothId() { return isVerifiedMobilityBoothId; }
    public void setIsVerifiedMobilityBoothId(Boolean isVerifiedMobilityBoothId) { this.isVerifiedMobilityBoothId = isVerifiedMobilityBoothId; }
    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
    public Date getDeletedAt() { return deletedAt; }
    public void setDeletedAt(Date deletedAt) { this.deletedAt = deletedAt; }
    public String getDeletionReason() { return deletionReason; }
    public void setDeletionReason(String deletionReason) { this.deletionReason = deletionReason; }
    public String getDeletedBy() { return deletedBy; }
    public void setDeletedBy(String deletedBy) { this.deletedBy = deletedBy; }
}
