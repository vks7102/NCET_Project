package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.User;
import com.capstone.backend.model.Voter;
import com.capstone.backend.repository.UserRepository;
import com.capstone.backend.repository.VoterRepository;
import com.capstone.backend.utils.VoterUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OfficerDashboardService {

    private final UserRepository userRepository;
    private final VoterRepository voterRepository;

    public OfficerDashboardService(UserRepository userRepository, VoterRepository voterRepository) {
        this.userRepository = userRepository;
        this.voterRepository = voterRepository;
    }

    public List<User> getPendingUsers(String role) {
        return switch (role) {
            case "BLO" -> userRepository.findPendingBLO();
            case "ERO" -> userRepository.findPendingERO();
            case "DEO" -> userRepository.findPendingDEO();
            default -> throw new ApiError(403, "You don't have permission to view pending users");
        };
    }

    public User verifyUser(String userId, String remarks, String role) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiError(404, "User not found"));

        List<User.Verification> verification = user.getVerification();
        if (verification == null) {
            throw new ApiError(400, "Verification not found");
        }

        switch (role) {
            case "BLO" -> verifyBLO(user, verification, remarks);
            case "ERO" -> verifyERO(user, verification, remarks);
            case "DEO" -> {
                verifyDEO(user, verification, remarks);
                Voter voter = convertUserToVoter(user);
                return user;
            }
            default -> throw new ApiError(403, "You don't have permission to verify users");
        }

        userRepository.save(user);
        return user;
    }

    public User rejectUser(String userId, String remarks, String role) {
        if (remarks == null || remarks.isBlank()) {
            throw new ApiError(400, "Remarks are required when rejecting a user");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiError(404, "User not found"));

        List<User.Verification> verification = user.getVerification();
        if (verification == null) {
            throw new ApiError(400, "Verification not found");
        }

        switch (role) {
            case "BLO" -> rejectBLO(user, verification, remarks);
            case "ERO" -> rejectERO(user, verification, remarks);
            case "DEO" -> rejectDEO(user, verification, remarks);
            default -> throw new ApiError(403, "You don't have permission to reject users");
        }

        userRepository.save(user);
        return user;
    }

    public Voter convertToVoter(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiError(404, "User not found"));

        if (!VoterUtil.isUserFullyVerified(user.getVerification())) {
            throw new ApiError(400, "User must be verified by BLO, ERO, and DEO first");
        }

        if (voterRepository.findByAadharNumber(user.getAadharNumber()).isPresent()) {
            throw new ApiError(400, "Voter already exists for this user");
        }

        return convertUserToVoter(user);
    }

    public Map<String, Object> convertAllVerifiedToVoters() {
        List<User> users = userRepository.findDeoVerified();
        List<String> converted = new ArrayList<>();
        List<Map<String, String>> failed = new ArrayList<>();

        for (User user : users) {
            try {
                if (voterRepository.findByAadharNumber(user.getAadharNumber()).isPresent()) continue;
                if (!VoterUtil.isUserFullyVerified(user.getVerification())) continue;
                convertUserToVoter(user);
                converted.add(user.getAadharNumber());
            } catch (Exception e) {
                failed.add(Map.of("aadharNumber", user.getAadharNumber(), "error", e.getMessage()));
            }
        }

        return Map.of("converted", converted, "failed", failed);
    }

    private void verifyBLO(User user, List<User.Verification> verification, String remarks) {
        User.Verification blo = findVerification(verification, "BLO");
        if (!"pending".equals(blo.getStatus())) {
            throw new ApiError(400, "User has already been verified/rejected by BLO");
        }
        blo.setStatus("verified");
        blo.setRemarks(remarks != null ? remarks : "");
        blo.setVerifiedAt(new Date());
    }

    private void verifyERO(User user, List<User.Verification> verification, String remarks) {
        User.Verification blo = findVerification(verification, "BLO");
        User.Verification ero = findVerification(verification, "ERO");
        if (!"verified".equals(blo.getStatus())) {
            throw new ApiError(400, "User must be verified by BLO first");
        }
        if (!"pending".equals(ero.getStatus())) {
            throw new ApiError(400, "User has already been verified/rejected by ERO");
        }
        ero.setStatus("verified");
        ero.setRemarks(remarks != null ? remarks : "");
        ero.setVerifiedAt(new Date());
    }

    private void verifyDEO(User user, List<User.Verification> verification, String remarks) {
        User.Verification blo = findVerification(verification, "BLO");
        User.Verification ero = findVerification(verification, "ERO");
        User.Verification deo = findVerification(verification, "DEO");
        if (!"verified".equals(blo.getStatus())) {
            throw new ApiError(400, "User must be verified by BLO first");
        }
        if (!"verified".equals(ero.getStatus())) {
            throw new ApiError(400, "User must be verified by ERO first");
        }
        if (!"pending".equals(deo.getStatus())) {
            throw new ApiError(400, "User has already been verified/rejected by DEO");
        }
        deo.setStatus("verified");
        deo.setRemarks(remarks != null ? remarks : "");
        deo.setVerifiedAt(new Date());
    }

    private void rejectBLO(User user, List<User.Verification> verification, String remarks) {
        User.Verification blo = findVerification(verification, "BLO");
        if (!"pending".equals(blo.getStatus())) {
            throw new ApiError(400, "User has already been verified/rejected by BLO");
        }
        blo.setStatus("rejected");
        blo.setRemarks(remarks);
        blo.setVerifiedAt(new Date());
    }

    private void rejectERO(User user, List<User.Verification> verification, String remarks) {
        User.Verification blo = findVerification(verification, "BLO");
        User.Verification ero = findVerification(verification, "ERO");
        if (!"verified".equals(blo.getStatus())) {
            throw new ApiError(400, "User must be verified by BLO first");
        }
        if (!"pending".equals(ero.getStatus())) {
            throw new ApiError(400, "User has already been verified/rejected by ERO");
        }
        ero.setStatus("rejected");
        ero.setRemarks(remarks);
        ero.setVerifiedAt(new Date());
    }

    private void rejectDEO(User user, List<User.Verification> verification, String remarks) {
        User.Verification blo = findVerification(verification, "BLO");
        User.Verification ero = findVerification(verification, "ERO");
        User.Verification deo = findVerification(verification, "DEO");
        if (!"verified".equals(blo.getStatus())) {
            throw new ApiError(400, "User must be verified by BLO first");
        }
        if (!"verified".equals(ero.getStatus())) {
            throw new ApiError(400, "User must be verified by ERO first");
        }
        if (!"pending".equals(deo.getStatus())) {
            throw new ApiError(400, "User has already been verified/rejected by DEO");
        }
        deo.setStatus("rejected");
        deo.setRemarks(remarks);
        deo.setVerifiedAt(new Date());
    }

    private User.Verification findVerification(List<User.Verification> verification, String level) {
        return verification.stream()
                .filter(v -> level.equals(v.getLevel()))
                .findFirst()
                .orElseThrow(() -> new ApiError(400, level + " verification not found"));
    }

    private Voter convertUserToVoter(User user) {
        String uniqueVoterId;
        while (true) {
            uniqueVoterId = VoterUtil.generateVoterId(user.getState() != null ? user.getState() : "UNK");
            if (voterRepository.findByUniqueVoterId(uniqueVoterId).isEmpty()) break;
        }

        Voter voter = new Voter();
        voter.setState(user.getState() != null ? user.getState() : "");
        voter.setDistrict(user.getDistrict() != null ? user.getDistrict() :
                user.getAddress() != null ? user.getAddress().getDistrict() : "");
        voter.setAssembley(user.getAssembley() != null ? user.getAssembley() : "");
        voter.setBoothNumber(user.getBoothNumber() != null ? user.getBoothNumber() : "");
        voter.setConsituency(user.getConsituency() != null ? user.getConsituency() : "");
        voter.setFirstName(user.getFirstName() != null ? user.getFirstName() : "");
        voter.setLastName(user.getLastName() != null ? user.getLastName() : "");
        voter.setImageUrl(user.getImageUrl() != null ? user.getImageUrl() : "");
        voter.setPassword(user.getPassword() != null ? user.getPassword() : user.getAadharNumber());

        if (user.getRelative() != null) {
            Voter.Relative rel = new Voter.Relative();
            rel.setType(user.getRelative().getType());
            rel.setName(user.getRelative().getName());
            voter.setRelative(rel);
        }

        voter.setPhoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : "");
        voter.setEmail(user.getEmail() != null ? user.getEmail() : "");
        voter.setAadharNumber(user.getAadharNumber() != null ? user.getAadharNumber() : "");
        voter.setGender(user.getGender() != null ? user.getGender() : "other");
        voter.setDob(user.getDob() != null ? user.getDob() : new Date());

        if (user.getAddress() != null) {
            Voter.Address addr = new Voter.Address();
            addr.setHouseNumber(user.getAddress().getHouseNumber());
            addr.setVillage(user.getAddress().getVillage());
            addr.setTehsil(user.getAddress().getTehsil());
            addr.setPostOffice(user.getAddress().getPostOffice());
            addr.setPoliceStation(user.getAddress().getPoliceStation());
            addr.setDistrict(user.getAddress().getDistrict() != null ? user.getAddress().getDistrict() : user.getDistrict());
            addr.setState(user.getAddress().getState() != null ? user.getAddress().getState() : user.getState());
            addr.setPincode(user.getAddress().getPincode());
            voter.setAddress(addr);
        }

        if (user.getDisability() != null) {
            Voter.Disability dis = new Voter.Disability();
            dis.setType(user.getDisability().getType());
            dis.setCertificate(user.getDisability().getCertificate());
            voter.setDisability(dis);
        }

        voter.setReferenceId(user.getReferenceId() != null ? user.getReferenceId() : "");
        voter.setUniqueVoterId(uniqueVoterId);

        return voterRepository.save(voter);
    }
}
