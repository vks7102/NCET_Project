package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.Booth;
import com.capstone.backend.service.BoothService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/booths")
public class BoothController {

    private final BoothService boothService;

    public BoothController(BoothService boothService) {
        this.boothService = boothService;
    }

    @GetMapping("/{acsCode}")
    public ResponseEntity<ApiResponse<?>> getBoothsByACSCode(@PathVariable String acsCode) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Booths retrieved successfully", boothService.getBoothsByAcCode(acsCode)));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllBooths(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam Map<String, String> filter) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Booths fetched successfully", boothService.getAllBooths(page, limit, filter)));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createBooth(@RequestBody Booth booth) {
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Booth created successfully", boothService.createBooth(booth)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateBooth(@PathVariable String id, @RequestBody Booth booth) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Booth updated successfully", boothService.updateBooth(id, booth)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteBooth(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Booth deleted successfully", boothService.deleteBooth(id)));
    }
}
