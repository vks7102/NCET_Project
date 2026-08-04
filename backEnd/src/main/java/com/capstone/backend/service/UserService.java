package com.capstone.backend.service;

import com.capstone.backend.exception.ApiError;
import com.capstone.backend.model.User;
import com.capstone.backend.model.Voter;
import com.capstone.backend.repository.UserRepository;
import com.capstone.backend.repository.VoterRepository;
import com.capstone.backend.utils.VoterUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final VoterRepository voterRepository;

    public UserService(UserRepository userRepository, VoterRepository voterRepository) {
        this.userRepository = userRepository;
        this.voterRepository = voterRepository;
    }

    public User createUser(User userData) {
        String referenceId = VoterUtil.generateReferenceId();

        List<User.Verification> verification = new ArrayList<>();
        for (String level : List.of("BLO", "ERO", "DEO", "AI")) {
            User.Verification v = new User.Verification();
            v.setLevel(level);
            v.setStatus("pending");
            v.setRemarks(level.equals("AI") ? "Verification in progress" : "");
            v.setVerifiedAt(null);
            verification.add(v);
        }

        userRepository.findByAadharOrPhoneOrEmail(
                userData.getAadharNumber(),
                userData.getPhoneNumber(),
                userData.getEmail()
        ).ifPresent(u -> {
            throw new ApiError(400, "User with same Aadhar number, phone number or email already exists");
        });

        if (userData.getRelative() != null && userData.getRelative().getAadharNumber() != null) {
            voterRepository.findByAadharNumber(userData.getRelative().getAadharNumber())
                    .orElseThrow(() -> new ApiError(400,
                            "Relative with given Aadhar number does not exist in voters database"));
        }

        userData.setReferenceId(referenceId);
        userData.setVerification(verification);

        return userRepository.save(userData);
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ApiError(404, "No users found");
        }
        return users;
    }

    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ApiError(404, "User not found"));
    }
}
