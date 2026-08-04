package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.Acs;
import com.capstone.backend.service.AcsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/acs")
public class ACController {

    private final AcsService acsService;

    public ACController(AcsService acsService) {
        this.acsService = acsService;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getAllACsList() {
        return ResponseEntity.ok(new ApiResponse<>(200, "ACs list retrieved successfully", acsService.getAllAcsList()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllACS(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(new ApiResponse<>(200, "All ACS retrieved successfully", acsService.getAllAcs(params)));
    }

    @GetMapping("/by-pc/{pcCode}")
    public ResponseEntity<ApiResponse<?>> getACSByPCCode(@PathVariable String pcCode) {
        return ResponseEntity.ok(new ApiResponse<>(200, "ACS retrieved successfully", acsService.getAcsByPcCode(pcCode)));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createAC(@RequestBody Acs acs) {
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "AC created successfully", acsService.createAc(acs)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateAC(@PathVariable String id, @RequestBody Acs acs) {
        return ResponseEntity.ok(new ApiResponse<>(200, "AC updated successfully", acsService.updateAc(id, acs)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteAC(@PathVariable String id) {
        acsService.deleteAc(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "AC deleted successfully"));
    }
}
