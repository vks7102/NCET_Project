package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.Booth;
import com.capstone.backend.model.MobilityBooth;
import com.capstone.backend.model.Officer;
import com.capstone.backend.model.PollingBoothOfficer;
import com.capstone.backend.repository.BoothRepository;
import com.capstone.backend.repository.MobilityBoothRepository;
import com.capstone.backend.repository.PollingBoothOfficerRepository;
import com.capstone.backend.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PollingBoothOfficerService {

    private final PollingBoothOfficerRepository pollingBoothOfficerRepository;
    private final BoothRepository boothRepository;
    private final MobilityBoothRepository mobilityBoothRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public PollingBoothOfficerService(PollingBoothOfficerRepository pollingBoothOfficerRepository,
                                      BoothRepository boothRepository,
                                      MobilityBoothRepository mobilityBoothRepository,
                                      PasswordEncoder passwordEncoder,
                                      JwtTokenProvider jwtTokenProvider) {
        this.pollingBoothOfficerRepository = pollingBoothOfficerRepository;
        this.boothRepository = boothRepository;
        this.mobilityBoothRepository = mobilityBoothRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public PollingBoothOfficer createPollingBoothOfficer(Officer eroOfficer, PollingBoothOfficer officerData) {
        String boothId = null;
        String mobilityBoothId = null;

        if ("booth".equals(officerData.getAssignmentType()) && officerData.getBooth() != null) {
            Booth booth = boothRepository.findById(officerData.getBooth())
                    .orElseThrow(() -> new ApiError(404, "Booth not found"));
            boothId = booth.getId();
        } else if ("mobility_booth".equals(officerData.getAssignmentType()) && officerData.getMobilityBooth() != null) {
            MobilityBooth mb = mobilityBoothRepository.findById(officerData.getMobilityBooth())
                    .orElseThrow(() -> new ApiError(404, "Mobility booth not found"));
            mobilityBoothId = mb.getId();
        }

        PollingBoothOfficer officer = new PollingBoothOfficer();
        officer.setName(officerData.getName());
        officer.setEmail(officerData.getEmail());
        officer.setPassword(passwordEncoder.encode(officerData.getPassword()));
        officer.setPhoneNumber(officerData.getPhoneNumber());
        officer.setAssignmentType(officerData.getAssignmentType());
        officer.setBooth(boothId);
        officer.setMobilityBooth(mobilityBoothId);
        officer.setIsAssigned(boothId != null || mobilityBoothId != null);
        officer.setEro(eroOfficer.getId());
        officer.setCreatedAt(new Date());
        officer.setUpdatedAt(new Date());

        return pollingBoothOfficerRepository.save(officer);
    }

    public Map<String, Object> loginPollingBoothOfficer(String email, String password) {
        PollingBoothOfficer officer = pollingBoothOfficerRepository.findByEmail(email)
                .orElseThrow(() -> new ApiError(401, "Invalid credentials"));

        if (!passwordEncoder.matches(password, officer.getPassword())) {
            throw new ApiError(401, "Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(officer.getId(), "POLLING_BOOTH_OFFICER");
        return Map.of("officer", officer, "token", token);
    }

    public List<PollingBoothOfficer> getMyPollingBoothOfficers(Officer eroOfficer) {
        return pollingBoothOfficerRepository.findByEro(eroOfficer.getId());
    }

    public List<Booth> getAllBooths() {
        return boothRepository.findAll();
    }

    public List<MobilityBooth> getAllMobilityBooths() {
        return mobilityBoothRepository.findAll();
    }

    public PollingBoothOfficer assignBoothToOfficer(String officerId, String boothId) {
        PollingBoothOfficer officer = pollingBoothOfficerRepository.findById(officerId)
                .orElseThrow(() -> new ApiError(404, "Officer not found"));
        Booth booth = boothRepository.findById(boothId)
                .orElseThrow(() -> new ApiError(404, "Booth not found"));

        officer.setBooth(booth.getId());
        officer.setMobilityBooth(null);
        officer.setAssignmentType("booth");
        officer.setIsAssigned(true);
        return pollingBoothOfficerRepository.save(officer);
    }

    public PollingBoothOfficer assignMobilityBoothToOfficer(String officerId, String mobilityBoothId) {
        PollingBoothOfficer officer = pollingBoothOfficerRepository.findById(officerId)
                .orElseThrow(() -> new ApiError(404, "Officer not found"));
        MobilityBooth mb = mobilityBoothRepository.findById(mobilityBoothId)
                .orElseThrow(() -> new ApiError(404, "Mobility booth not found"));

        officer.setMobilityBooth(mb.getId());
        officer.setBooth(null);
        officer.setAssignmentType("mobility_booth");
        officer.setIsAssigned(true);
        return pollingBoothOfficerRepository.save(officer);
    }
}
