package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.Officer;
import com.capstone.backend.security.OfficerPrincipal;
import com.capstone.backend.service.OfficerService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/officers")
public class OfficerController {

    private final OfficerService officerService;

    public OfficerController(OfficerService officerService) {
        this.officerService = officerService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> loginOfficer(@RequestBody Map<String, String> body, HttpServletResponse response) {
        var result = officerService.loginOfficer(body.get("email"), body.get("password"), body.get("role"));

        Cookie cookie = new Cookie("token", (String) result.get("token"));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60);
        response.addCookie(cookie);

        return ResponseEntity.ok(new ApiResponse<>(200, "Login successful", result.get("officer")));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createOfficer(
            @AuthenticationPrincipal OfficerPrincipal principal,
            @RequestBody Officer officerData) {
        Officer currentOfficer = officerService.getOfficerById(principal.getId());
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Officer created successfully",
                officerService.createOfficer(currentOfficer, officerData)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getCurrentOfficer(@AuthenticationPrincipal OfficerPrincipal principal) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Officer fetched successfully",
                officerService.getOfficerById(principal.getId())));
    }

    @GetMapping("/my-officers")
    public ResponseEntity<ApiResponse<?>> getMyOfficers(@AuthenticationPrincipal OfficerPrincipal principal) {
        Officer currentOfficer = officerService.getOfficerById(principal.getId());
        return ResponseEntity.ok(new ApiResponse<>(200, "Officers fetched successfully",
                officerService.getMyOfficers(currentOfficer)));
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<?>> getOfficersByRole(@PathVariable String role) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Officers fetched successfully",
                officerService.getOfficersByRole(role)));
    }
}
