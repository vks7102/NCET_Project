package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.Officer;
import com.capstone.backend.security.OfficerPrincipal;
import com.capstone.backend.service.DashboardService;
import com.capstone.backend.service.OfficerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final OfficerService officerService;

    public DashboardController(DashboardService dashboardService, OfficerService officerService) {
        this.dashboardService = dashboardService;
        this.officerService = officerService;
    }

    @GetMapping("/eci-stats")
    public ResponseEntity<ApiResponse<?>> getECIStats() {
        return ResponseEntity.ok(new ApiResponse<>(200, "ECI Stats retrieved successfully", dashboardService.getEciStats()));
    }

    @GetMapping("/ceo-stats")
    public ResponseEntity<ApiResponse<?>> getCEOStats(@AuthenticationPrincipal OfficerPrincipal principal) {
        Officer officer = officerService.getOfficerById(principal.getId());
        String state = officer.getPostingAddress().getState();
        return ResponseEntity.ok(new ApiResponse<>(200, "CEO Stats retrieved successfully", dashboardService.getCeoStats(state)));
    }

    @GetMapping("/deo-stats")
    public ResponseEntity<ApiResponse<?>> getDEOStats(@AuthenticationPrincipal OfficerPrincipal principal) {
        Officer officer = officerService.getOfficerById(principal.getId());
        String state = officer.getPostingAddress().getState();
        String district = officer.getPostingAddress().getDistrict();
        return ResponseEntity.ok(new ApiResponse<>(200, "DEO Stats retrieved successfully", dashboardService.getDeoStats(district, state)));
    }

    @GetMapping("/ero-stats")
    public ResponseEntity<ApiResponse<?>> getEROStats(@AuthenticationPrincipal OfficerPrincipal principal) {
        Officer officer = officerService.getOfficerById(principal.getId());
        String state = officer.getPostingAddress().getState();
        String district = officer.getPostingAddress().getDistrict();
        String assembly = officer.getPostingAddress().getAssembley();
        return ResponseEntity.ok(new ApiResponse<>(200, "ERO Stats retrieved successfully", dashboardService.getEroStats(assembly, district, state)));
    }

    @GetMapping("/blo-stats")
    public ResponseEntity<ApiResponse<?>> getBLOStats(@AuthenticationPrincipal OfficerPrincipal principal) {
        Officer officer = officerService.getOfficerById(principal.getId());
        String state = officer.getPostingAddress().getState();
        String district = officer.getPostingAddress().getDistrict();
        String assembly = officer.getPostingAddress().getAssembley();
        return ResponseEntity.ok(new ApiResponse<>(200, "BLO Stats retrieved successfully",
                dashboardService.getBloStats("", assembly, district, state)));
    }
}
