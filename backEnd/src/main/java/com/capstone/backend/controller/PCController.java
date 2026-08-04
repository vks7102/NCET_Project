package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.Pcs;
import com.capstone.backend.service.PcsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pcs")
public class PCController {

    private final PcsService pcsService;

    public PCController(PcsService pcsService) {
        this.pcsService = pcsService;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<?>> getAllPCsList() {
        return ResponseEntity.ok(new ApiResponse<>(200, "PCs list retrieved successfully", pcsService.getAllPcsList()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllPCS(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(new ApiResponse<>(200, "All PCS retrieved successfully", pcsService.getAllPcs(params)));
    }

    @GetMapping("/by-state/{stateCode}")
    public ResponseEntity<ApiResponse<?>> getPCSByStateCode(@PathVariable String stateCode) {
        return ResponseEntity.ok(new ApiResponse<>(200, "PCS retrieved successfully", pcsService.getPcsByStateCode(stateCode)));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createPC(@RequestBody Pcs pcs) {
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "PC created successfully", pcsService.createPc(pcs)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updatePC(@PathVariable String id, @RequestBody Pcs pcs) {
        return ResponseEntity.ok(new ApiResponse<>(200, "PC updated successfully", pcsService.updatePc(id, pcs)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deletePC(@PathVariable String id) {
        pcsService.deletePc(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "PC deleted successfully"));
    }
}
