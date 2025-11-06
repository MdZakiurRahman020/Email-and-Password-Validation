package com.example.emailpasswordapp.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for password encryption and hashing.
 * Uses SHA-256 for secure one-way hashing of passwords.
 */
public final class Encryptor {

    private Encryptor() {
        // Prevent instantiation — utility class
    }

    /**
     * Hashes a given password using SHA-256 and returns its hex representation.
     *
     * @param password Plain text password
     * @return 64-character hexadecimal SHA-256 hash
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashedBytes);

        } catch (NoSuchAlgorithmException e) {
            // This should never happen in modern JVMs
            System.err.println("❌ SHA-256 algorithm not found: " + e.getMessage());
            return "";
        }
    }

    /**
     * Converts a byte array into a hexadecimal string.
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}
