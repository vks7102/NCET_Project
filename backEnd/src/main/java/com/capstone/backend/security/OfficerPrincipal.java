package com.capstone.backend.security;

import java.io.Serializable;

public class OfficerPrincipal implements Serializable {
    private final String id;
    private final String role;

    public OfficerPrincipal(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getId() { return id; }
    public String getRole() { return role; }
}
