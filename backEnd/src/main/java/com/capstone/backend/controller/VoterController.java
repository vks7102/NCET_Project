package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.security.OfficerPrincipal;
import com.capstone.backend.service.OfficerService;
import com.capstone.backend.service.VoterService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    private final VoterService voterService;
    private final OfficerService officerService;

    public VoterController(VoterService voterService, OfficerService officerService) {
        this.voterService = voterService;
        this.officerService = officerService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> loginVoter(@RequestBody Map<String, String> body) {
        var result = voterService.loginVoter(body.get("uniqueVoterId"), body.get("password"));
        return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", result));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllVoters(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam Map<String, String> filter) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Voters fetched successfully",
                voterService.getAllVoters(page, limit, filter)));
    }

    @GetMapping("/by-state")
    public ResponseEntity<ApiResponse<?>> getVotersByState(
            @RequestParam String state,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Voters fetched successfully",
                voterService.getVotersByState(state, page, limit)));
    }

    @GetMapping("/check-aadhar")
    public ResponseEntity<ApiResponse<?>> checkVoterAndUserViaAadhar(@RequestParam String aadharNumber) {
        var result = voterService.checkVoterAndUserViaAadhar(aadharNumber);
        boolean exists = (boolean) result.get("exists");
        if (exists) {
            return ResponseEntity.ok(new ApiResponse<>(200, "Voter exists with this Aadhar number", result));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "No voter or user exists with this Aadhar number", result));
    }

    @GetMapping("/by-booth")
    public ResponseEntity<ApiResponse<?>> getVotersByBoothId(@RequestParam String boothId) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Voters fetched successfully",
                voterService.getVotersByBoothId(boothId)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<?>> searchVoters(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Voters fetched successfully",
                voterService.searchVoters(params)));
    }

    @PostMapping("/mobility/assign")
    public ResponseEntity<ApiResponse<?>> assignMobilityBooth(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Mobility booth assigned successfully",
                voterService.assignMobilityBooth(body.get("voterId"), body.get("boothId"))));
    }

    @PostMapping("/mobility/verify")
    public ResponseEntity<ApiResponse<?>> verifyMobilityBooth(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Mobility booth verified successfully",
                voterService.verifyMobilityBooth(body.get("voterId"), Boolean.parseBoolean(body.get("isVerified")))));
    }

    @GetMapping("/mobility/requests")
    public ResponseEntity<ApiResponse<?>> getMobilityBoothRequests() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Mobility booth requests fetched successfully",
                voterService.getMobilityBoothRequests()));
    }

    @PostMapping("/mark-deleted")
    public ResponseEntity<ApiResponse<?>> markVoterAsDeleted(
            @AuthenticationPrincipal OfficerPrincipal principal,
            @RequestBody Map<String, String> body) {
        String officerName = officerService.getOfficerById(principal.getId()).getName();
        return ResponseEntity.ok(new ApiResponse<>(200, "Voter marked as deleted successfully",
                voterService.markVoterAsDeleted(body.get("voterId"), body.get("reason"), officerName)));
    }

    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<?>> getDeletedVoters(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Deleted voters fetched successfully",
                voterService.getDeletedVoters(page, limit)));
    }
}
