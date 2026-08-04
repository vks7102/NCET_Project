package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.Officer;
import com.capstone.backend.repository.OfficerRepository;
import com.capstone.backend.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OfficerService {

    private final OfficerRepository officerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    private static final Map<String, String> ROLE_HIERARCHY = Map.of(
            "ECI HQ", "CEO",
            "CEO", "DEO",
            "DEO", "ERO",
            "ERO", "BLO"
    );

    public OfficerService(OfficerRepository officerRepository, PasswordEncoder passwordEncoder,
                          JwtTokenProvider jwtTokenProvider) {
        this.officerRepository = officerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Officer createOfficer(Officer currentOfficer, Officer newOfficerData) {
        String allowedRole = ROLE_HIERARCHY.get(currentOfficer.getRole());
        if (allowedRole == null) {
            throw new ApiError(403, "You don't have permission to create officers");
        }

        if (newOfficerData.getRole() != null && !newOfficerData.getRole().equals(allowedRole)) {
            throw new ApiError(400, "You can only create " + allowedRole + " officers");
        }

        Officer officer = new Officer();
        officer.setName(newOfficerData.getName());
        officer.setEmail(newOfficerData.getEmail());
        officer.setPassword(passwordEncoder.encode(newOfficerData.getPassword()));
        officer.setPhoneNumber(newOfficerData.getPhoneNumber());
        officer.setRole(allowedRole);

        Officer.PostingAddress address = new Officer.PostingAddress();
        address.setState(newOfficerData.getPostingAddress() != null ? newOfficerData.getPostingAddress().getState() : "");
        address.setDistrict(newOfficerData.getPostingAddress() != null ? newOfficerData.getPostingAddress().getDistrict() : "");
        address.setAssembley(newOfficerData.getPostingAddress() != null ? newOfficerData.getPostingAddress().getAssembley() : "");
        address.setConsituency(newOfficerData.getPostingAddress() != null ? newOfficerData.getPostingAddress().getConsituency() : "");
        officer.setPostingAddress(address);

        officer.setCreatedAt(new Date());
        officer.setUpdatedAt(new Date());

        return officerRepository.save(officer);
    }

    public Map<String, Object> loginOfficer(String email, String password, String role) {
        Officer officer = officerRepository.findByEmailAndRole(email, role.toUpperCase())
                .orElseThrow(() -> new ApiError(401, "Invalid credentials"));

        if (!passwordEncoder.matches(password, officer.getPassword())) {
            throw new ApiError(401, "Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(officer.getId(), officer.getRole());
        return Map.of("officer", officer, "token", token);
    }

    public List<Officer> getOfficersByRole(String role) {
        return officerRepository.findByRole(role.toUpperCase());
    }

    public List<Officer> getMyOfficers(Officer currentOfficer) {
        String childRole = ROLE_HIERARCHY.get(currentOfficer.getRole());
        if (childRole == null) return List.of();
        return officerRepository.findByRole(childRole);
    }

    public Officer getOfficerById(String id) {
        return officerRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "Officer not found"));
    }
}
