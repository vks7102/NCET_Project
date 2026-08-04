package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.Voter;
import com.capstone.backend.repository.VoterRepository;
import com.capstone.backend.security.JwtTokenProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class VoterService {

    private final VoterRepository voterRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public VoterService(VoterRepository voterRepository, JwtTokenProvider jwtTokenProvider) {
        this.voterRepository = voterRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Map<String, Object> loginVoter(String uniqueVoterId, String password) {
        Voter voter = voterRepository.findByUniqueVoterId(uniqueVoterId)
                .orElseThrow(() -> new ApiError(404, "Voter not found"));

        if (!password.equals(voter.getPassword())) {
            throw new ApiError(401, "Invalid credentials");
        }

        String token = jwtTokenProvider.generateToken(voter.getId(), "voter");
        return Map.of("voter", voter, "token", token);
    }

    public Map<String, Object> getAllVoters(int page, int limit, Map<String, String> filter) {
        PageRequest pageable = PageRequest.of(page - 1, limit);
        Page<Voter> voterPage = voterRepository.findAll(pageable);

        return Map.of(
                "voters", voterPage.getContent(),
                "pagination", Map.of(
                        "page", page,
                        "limit", limit,
                        "total", voterPage.getTotalElements(),
                        "totalPages", voterPage.getTotalPages()
                )
        );
    }

    public Map<String, Object> getVotersByState(String state, int page, int limit) {
        if (state == null || state.isBlank()) {
            throw new ApiError(400, "State is required");
        }
        PageRequest pageable = PageRequest.of(page - 1, limit);
        List<Voter> voters = voterRepository.findByStateAndIsDeletedFalse(state);
        int total = voters.size();
        int start = (page - 1) * limit;
        int end = Math.min(start + limit, total);
        List<Voter> pageContent = start < total ? voters.subList(start, end) : List.of();

        return Map.of(
                "voters", pageContent,
                "pagination", Map.of("page", page, "limit", limit, "total", total, "totalPages", (int) Math.ceil((double) total / limit)),
                "stats", Map.of("totalVoters", total)
        );
    }

    public Map<String, Object> checkVoterAndUserViaAadhar(String aadharNumber) {
        Voter voter = voterRepository.findByAadharNumber(aadharNumber).orElse(null);
        return Map.of("exists", voter != null);
    }

    public List<Voter> getVotersByBoothId(String boothId) {
        if (boothId == null || boothId.isBlank()) {
            throw new ApiError(400, "Please provide boothId");
        }
        List<Voter> voters = voterRepository.findByMobilityBoothIdAndIsDeletedFalse(boothId);
        if (voters.isEmpty()) {
            voters = voterRepository.findByBoothNumberAndIsDeletedFalse(boothId);
        }
        return voters;
    }

    public Voter assignMobilityBooth(String voterId, String boothId) {
        Voter voter = voterRepository.findByUniqueVoterId(voterId)
                .orElseThrow(() -> new ApiError(404, "Voter not found"));
        voter.setMobilityBoothId(boothId);
        voter.setIsVerifiedMobilityBoothId(false);
        return voterRepository.save(voter);
    }

    public Voter verifyMobilityBooth(String voterId, boolean isVerified) {
        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() -> new ApiError(404, "Voter not found"));
        voter.setIsVerifiedMobilityBoothId(isVerified);
        return voterRepository.save(voter);
    }

    public List<Voter> getMobilityBoothRequests() {
        return voterRepository.findMobilityBoothRequests();
    }

    public Voter markVoterAsDeleted(String voterId, String reason, String officerName) {
        Voter voter = voterRepository.findById(voterId)
                .orElseThrow(() -> new ApiError(404, "Voter not found"));
        if (Boolean.TRUE.equals(voter.getIsDeleted())) {
            throw new ApiError(400, "Voter is already marked as deleted");
        }
        voter.setIsDeleted(true);
        voter.setDeletedAt(new Date());
        voter.setDeletionReason(reason);
        voter.setDeletedBy(officerName);
        return voterRepository.save(voter);
    }

    public Map<String, Object> getDeletedVoters(int page, int limit) {
        PageRequest pageable = PageRequest.of(page - 1, limit);
        Page<Voter> voterPage = voterRepository.findAll(pageable);

        return Map.of(
                "voters", voterPage.getContent(),
                "pagination", Map.of(
                        "page", page,
                        "limit", limit,
                        "total", voterPage.getTotalElements(),
                        "totalPages", voterPage.getTotalPages()
                )
        );
    }

    public List<Voter> searchVoters(Map<String, String> params) {
        String phoneNumber = params.get("phoneNumber");
        String aadharNumber = params.get("aadharNumber");
        String uniqueVoterId = params.get("uniqueVoterId");
        String name = params.get("name");

        if (phoneNumber != null) {
            return List.of();
        } else if (aadharNumber != null) {
            return voterRepository.findByAadharNumber(aadharNumber).map(List::of).orElse(List.of());
        } else if (uniqueVoterId != null) {
            return voterRepository.findByUniqueVoterId(uniqueVoterId).map(List::of).orElse(List.of());
        } else if (name != null) {
            return voterRepository.findAll();
        }

        return List.of();
    }
}
