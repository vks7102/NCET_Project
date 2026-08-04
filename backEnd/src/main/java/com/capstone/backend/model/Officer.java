package com.capstone.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "officers")
public class Officer {
    @Id
    private String id;
    private String role;
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private PostingAddress postingAddress;
    private Date createdAt;
    private Date updatedAt;

    public static class PostingAddress {
        private String state;
        private String district;
        private String assembley;
        private String consituency;

        public PostingAddress() {}

        public PostingAddress(String state, String district, String assembley, String consituency) {
            this.state = state;
            this.district = district;
            this.assembley = assembley;
            this.consituency = consituency;
        }

        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getDistrict() { return district; }
        public void setDistrict(String district) { this.district = district; }
        public String getAssembley() { return assembley; }
        public void setAssembley(String assembley) { this.assembley = assembley; }
        public String getConsituency() { return consituency; }
        public void setConsituency(String consituency) { this.consituency = consituency; }
    }

    public Officer() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public PostingAddress getPostingAddress() { return postingAddress; }
    public void setPostingAddress(PostingAddress postingAddress) { this.postingAddress = postingAddress; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
