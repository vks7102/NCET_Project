package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.State;
import com.capstone.backend.service.StateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/states")
public class StateController {

    private final StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getStates() {
        return ResponseEntity.ok(new ApiResponse<>(200, "States retrieved successfully", stateService.getAllStates()));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllStates(@RequestParam Map<String, String> params) {
        return ResponseEntity.ok(new ApiResponse<>(200, "All states retrieved successfully", stateService.getAllStatesPaginated(params)));
    }

    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<?>> getStatesStats() {
        return ResponseEntity.ok(new ApiResponse<>(200, "States count retrieved successfully", Map.of("count", stateService.getStatesCount())));
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createState(@RequestBody State state) {
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "State created successfully", stateService.createState(state)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateState(@PathVariable String id, @RequestBody State state) {
        return ResponseEntity.ok(new ApiResponse<>(200, "State updated successfully", stateService.updateState(id, state)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteState(@PathVariable String id) {
        stateService.deleteState(id);
        return ResponseEntity.ok(new ApiResponse<>(200, "State deleted successfully"));
    }
}
