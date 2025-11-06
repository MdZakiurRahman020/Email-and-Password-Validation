package com.example.emailpasswordapp.models;

import com.example.emailpasswordapp.utils.Encryptor;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class UserDatabase {
    private static final String FILE_PATH = "E:/downloads/EmailPasswordApp/users.txt";

    private static void ensureFileExists() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.getParentFile().mkdirs(); // ensure folder exists
            file.createNewFile();
        }
    }

    public static void addUser(String email, String hashedPassword) throws IOException {
        ensureFileExists();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(email + "," + hashedPassword);
            writer.newLine();
        }
    }

    private static Map<String, String> getAllUsers() throws IOException {
        ensureFileExists();
        Map<String, String> users = new HashMap<>();

        List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 2) {
                users.put(parts[0].trim(), parts[1].trim());
            }
        }

        return users;
    }

    public static boolean userExists(String email) throws IOException {
        return getAllUsers().containsKey(email);
    }

    /**
     * ✅ Validates login credentials:
     * Hashes entered password and compares with stored hash.
     */
    public static boolean validateUser(String email, String plainPassword) throws IOException {
        Map<String, String> users = getAllUsers();

        if (!users.containsKey(email)) {
            System.out.println("⚠️ No such user: " + email);
            return false;
        }

        String storedHash = users.get(email);
        String enteredHash = Encryptor.hashPassword(plainPassword);

        System.out.println("🟦 [DEBUG] Entered hash: " + enteredHash);
        System.out.println("🟩 [DEBUG] Stored hash:  " + storedHash);

        return storedHash.equals(enteredHash);
    }
}
