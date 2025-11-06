package com.example.emailpasswordapp.models;

import java.util.Objects;

/**
 * Represents a user with an email and an encrypted password.
 */
public class User {

    private String email;
    private String hashedPassword;

    // ===== Constructor =====
    public User(String email, String hashedPassword) {
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    // ===== Getters =====
    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    // ===== Setters =====
    public void setEmail(String email) {
        this.email = email;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    // ===== Utility Methods =====

    /**
     * Converts the user object into a single line for file storage.
     * Example: user@example.com,3a7bd3a3c...
     */
    @Override
    public String toString() {
        return email + "," + hashedPassword;
    }

    /**
     * Parses a User object from a line of the database file.
     * Returns null if the line is invalid.
     */
    public static User fromString(String line) {
        if (line == null || !line.contains(",")) return null;

        String[] parts = line.split(",", 2); // split only once
        if (parts.length < 2) return null;

        return new User(parts[0].trim(), parts[1].trim());
    }

    /**
     * Two users are considered equal if they share the same email.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
