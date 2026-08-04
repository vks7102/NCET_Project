package com.capstone.backend.controller;

import com.capstone.backend.exception.ApiResponse;
import com.capstone.backend.model.User;
import com.capstone.backend.service.CloudinaryService;
import com.capstone.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService, CloudinaryService cloudinaryService, ObjectMapper objectMapper) {
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createUser(
            @RequestParam("imageUrl") MultipartFile file,
            @RequestParam("data") String userDataJson) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(400, "No file uploaded"));
        }

        String imageUrl = cloudinaryService.uploadFile(file);
        User userData = objectMapper.readValue(userDataJson, User.class);
        userData.setImageUrl(imageUrl);

        var newUser = userService.createUser(userData);
        return ResponseEntity.status(201).body(new ApiResponse<>(201, "User created successfully", newUser));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getUsers() {
        return ResponseEntity.ok(new ApiResponse<>(200, "Users retrieved successfully", userService.getUsers()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(new ApiResponse<>(200, "User retrieved successfully", userService.getUserById(id)));
    }
}
