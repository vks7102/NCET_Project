package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.MobilityBooth;
import com.capstone.backend.service.MobilityBoothService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mobility-booths")
public class MobilityBoothController {

    private final MobilityBoothService mobilityBoothService;

    public MobilityBoothController(MobilityBoothService mobilityBoothService) {
        this.mobilityBoothService = mobilityBoothService;
    }

    @GetMapping("/nearest")
    public ResponseEntity<ApiResponse<?>> nearestMobilityBooths(
            @RequestParam double lat, @RequestParam double longg) {
        var booths = mobilityBoothService.getNearestMobilityBooths(lat, longg);
        if (booths.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(200, "No mobility booths found within 20 kilometers.", java.util.List.of()));
        }
        return ResponseEntity.ok(new ApiResponse<>(200, "Nearest mobility booths retrieved successfully.", booths));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllMobilityBooths(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(new ApiResponse<>(200, "All mobility booths retrieved successfully", mobilityBoothService.getAllMobilityBooths(params)));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createMobilityBooth(@RequestBody MobilityBooth booth) {
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "Mobility booth created successfully", mobilityBoothService.createMobilityBooth(booth)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateMobilityBooth(@PathVariable String id, @RequestBody MobilityBooth booth) {
        return ResponseEntity.ok(new ApiResponse<>(200, "Mobility booth updated successfully", mobilityBoothService.updateMobilityBooth(id, booth)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteMobilityBooth(@PathVariable String id) {
        mobilityBoothService.deleteMobilityBooth(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "Mobility booth deleted successfully"));
    }
}
