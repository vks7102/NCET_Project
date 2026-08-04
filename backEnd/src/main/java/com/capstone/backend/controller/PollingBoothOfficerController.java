package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.Officer;
import com.capstone.backend.security.OfficerPrincipal;
import com.capstone.backend.service.OfficerService;
import com.capstone.backend.service.PollingBoothOfficerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/polling-booth-officers")
public class PollingBoothOfficerController {

    private final PollingBoothOfficerService pollingBoothOfficerService;
    private final OfficerService officerService;

    public PollingBoothOfficerController(PollingBoothOfficerService pollingBoothOfficerService,
                                         OfficerService officerService) {
        this.pollingBoothOfficerService = pollingBoothOfficerService;
        this.officerService = officerService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody Map<String, String> body, HttpServletResponse response) {
        var result = pollingBoothOfficerService.loginPollingBoothOfficer(body.get("email"), body.get("password"));

        Cookie cookie = new Cookie("token", (String) result.get("token"));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", result.get("officer")));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> create(
            @AuthenticationPrincipal OfficerPrincipal principal,
            @RequestBody com.capstone.backend.model.PollingBoothOfficer officerData) {
        Officer eroOfficer = officerService.getOfficerById(principal.getId());
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Polling booth officer created successfully",
                pollingBoothOfficerService.createPollingBoothOfficer(eroOfficer, officerData)));
    }

    @GetMapping("/my-polling-booth-officers")
    public ResponseEntity<ApiResponse<?>> getMyOfficers(@AuthenticationPrincipal OfficerPrincipal principal) {
        Officer eroOfficer = officerService.getOfficerById(principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(200, "Polling booth officers fetched successfully",
                pollingBoothOfficerService.getMyPollingBoothOfficers(eroOfficer)));
    }

    @GetMapping("/booths")
    public ResponseEntity<ApiResponse<?>> getBoothsForAssignment() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Booths fetched successfully",
                pollingBoothOfficerService.getAllBooths()));
    }

    @GetMapping("/mobility-booths")
    public ResponseEntity<ApiResponse<?>> getMobilityBoothsForAssignment() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Mobility booths fetched successfully",
                pollingBoothOfficerService.getAllMobilityBooths()));
    }

    @PostMapping("/assign-booth")
    public ResponseEntity<ApiResponse<?>> assignBooth(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Booth assigned successfully",
                pollingBoothOfficerService.assignBoothToOfficer(body.get("officerId"), body.get("boothId"))));
    }

    @PostMapping("/assign-mobility-booth")
    public ResponseEntity<ApiResponse<?>> assignMobilityBooth(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Mobility booth assigned successfully",
                pollingBoothOfficerService.assignMobilityBoothToOfficer(body.get("officerId"), body.get("mobilityBoothId"))));
    }
}
