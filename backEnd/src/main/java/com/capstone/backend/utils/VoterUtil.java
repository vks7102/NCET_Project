package com.capstone.backend.utils;

import com.capstone.backend.model.User;
import java.util.List;
import java.util.Random;

public class VoterUtil {

    private static final Random RANDOM = new Random();

    public static String generateVoterId(String state) {
        String prefix = state.substring(0, Math.min(3, state.length())).toUpperCase();
        int random = 100000000 + RANDOM.nextInt(900000000);
        return prefix + random;
    }

    public static boolean isUserFullyVerified(List<User.Verification> verification) {
        if (verification == null || verification.isEmpty()) return false;
        List<String> requiredLevels = List.of("BLO", "ERO", "DEO");
        return requiredLevels.stream().allMatch(level ->
                verification.stream().anyMatch(v ->
                        level.equals(v.getLevel()) && "verified".equals(v.getStatus())
                )
        );
    }

    public static String generateReferenceId() {
        return "ONOE-" + System.currentTimeMillis() + "-" +
                Integer.toHexString((int) (Math.random() * 0xFFFFFFFF));
    }
}
