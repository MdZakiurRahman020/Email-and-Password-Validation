package com.example.emailpasswordapp.controllers;

import com.example.emailpasswordapp.models.UserDatabase;
import com.example.emailpasswordapp.utils.Encryptor;
import com.example.emailpasswordapp.utils.Validator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class SignupController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private Button togglePasswordButton;
    @FXML private Label statusLabel;

    private boolean isPasswordVisible = false;

    /**
     * Handles user signup process
     */
    @FXML
    private void handleSignup() {
        String email = emailField.getText().trim();
        String password = isPasswordVisible
                ? visiblePasswordField.getText().trim()
                : passwordField.getText().trim();

        // Input validation
        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("⚠️ Please fill in all fields.");
            return;
        }

        if (!Validator.isValidEmail(email)) {
            statusLabel.setText("❌ Invalid email format!");
            return;
        }

        if (!Validator.isValidPassword(password)) {
            statusLabel.setText("Password must have:\n• Min 8 chars\n• 1 uppercase\n• 1 lowercase\n• 1 number\n• 1 special character");
            return;
        }

        try {
            if (UserDatabase.userExists(email)) {
                statusLabel.setText("⚠️ This email is already registered.");
                return;
            }

            // Encrypt password before saving
            String hashedPassword = Encryptor.hashPassword(password);
            UserDatabase.addUser(email, hashedPassword);

            statusLabel.setText("✅ Account created successfully! Redirecting...");

            // Auto-redirect to login after delay
            new Thread(() -> {
                try {
                    Thread.sleep(1200);
                    javafx.application.Platform.runLater(() -> {
                        try {
                            switchToLogin();
                        } catch (IOException e) {
                            statusLabel.setText("⚠️ Error returning to login!");
                        }
                    });
                } catch (InterruptedException ignored) {}
            }).start();

        } catch (IOException e) {
            statusLabel.setText("⚠️ Error saving user data!");
            e.printStackTrace();
        }
    }

    /**
     * Toggles visibility between hidden and visible password fields
     */
    @FXML
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide password
            passwordField.setText(visiblePasswordField.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            visiblePasswordField.setVisible(false);
            visiblePasswordField.setManaged(false);
            togglePasswordButton.setText("👁");
        } else {
            // Show password
            visiblePasswordField.setText(passwordField.getText());
            visiblePasswordField.setVisible(true);
            visiblePasswordField.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            togglePasswordButton.setText("🙈");
        }
        isPasswordVisible = !isPasswordVisible;
    }

    /**
     * Switches to the login page safely
     */
    @FXML
    private void switchToLogin() throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/com/example/emailpasswordapp/ui/login_neumorphic.fxml"
        ));

        if (loader.getLocation() == null) {
            System.err.println("❌ Could not find login_neumorphic.fxml — check your resource path!");
            return;
        }

        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
