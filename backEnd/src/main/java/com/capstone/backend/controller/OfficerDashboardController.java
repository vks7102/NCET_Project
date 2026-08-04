package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.Officer;
import com.capstone.backend.security.OfficerPrincipal;
import com.capstone.backend.service.OfficerDashboardService;
import com.capstone.backend.service.OfficerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/officer-dashboard")
public class OfficerDashboardController {

    private final OfficerDashboardService officerDashboardService;
    private final OfficerService officerService;

    public OfficerDashboardController(OfficerDashboardService officerDashboardService, OfficerService officerService) {
        this.officerDashboardService = officerDashboardService;
        this.officerService = officerService;
    }

    @GetMapping("/pending-users")
    public ResponseEntity<ApiResponse<?>> getPendingUsers(@AuthenticationPrincipal OfficerPrincipal principal) {
        Officer officer = officerService.getOfficerById(principal.getId());
        var users = officerDashboardService.getPendingUsers(officer.getRole());
        return ResponseEntity.ok(new ApiResponse<>(200, "Pending users fetched successfully", users));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<?>> verifyUser(
            @AuthenticationPrincipal OfficerPrincipal principal,
            @RequestBody Map<String, String> body) {
        Officer officer = officerService.getOfficerById(principal.getId());
        var result = officerDashboardService.verifyUser(body.get("userId"), body.get("remarks"), officer.getRole());
        return ResponseEntity.ok(new ApiResponse<>(200, "User verified by " + officer.getRole() + " successfully", result));
    }

    @PostMapping("/reject")
    public ResponseEntity<ApiResponse<?>> rejectUser(
            @AuthenticationPrincipal OfficerPrincipal principal,
            @RequestBody Map<String, String> body) {
        Officer officer = officerService.getOfficerById(principal.getId());
        var result = officerDashboardService.rejectUser(body.get("userId"), body.get("remarks"), officer.getRole());
        return ResponseEntity.ok(new ApiResponse<>(200, "User rejected by " + officer.getRole() + " successfully", result));
    }

    @PostMapping("/convert-to-voter")
    public ResponseEntity<ApiResponse<?>> convertToVoter(@RequestBody Map<String, String> body) {
        var voter = officerDashboardService.convertToVoter(body.get("userId"));
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "User converted to voter successfully", voter));
    }

    @PostMapping("/convert-all-to-voters")
    public ResponseEntity<ApiResponse<?>> convertAllVerifiedToVoters() {
        var result = officerDashboardService.convertAllVerifiedToVoters();
        return ResponseEntity.ok(new ApiResponse<>(200, "Batch conversion completed", result));
    }
}
