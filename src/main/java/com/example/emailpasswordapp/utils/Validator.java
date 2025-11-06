package com.example.emailpasswordapp.utils;

import java.util.regex.Pattern;

public final class Validator {

    private Validator() {}

    // ✅ Practical email validation — allows numbers, dots, underscores, and multi-level domains
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    // ✅ Password: Min 8 chars, 1 lowercase, 1 uppercase, 1 digit, 1 special (including @, _, -, etc.)
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&_\\-])[A-Za-z\\d@$!%*?&_\\-]{8,}$"
    );

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.trim().isEmpty()) return false;
        return PASSWORD_PATTERN.matcher(password.trim()).matches();
    }
}
