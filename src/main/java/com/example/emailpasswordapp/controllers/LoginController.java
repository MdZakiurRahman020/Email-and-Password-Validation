package com.example.emailpasswordapp.controllers;

import com.example.emailpasswordapp.models.UserDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private TextField visiblePasswordField;
    @FXML private Button togglePasswordButton;
    @FXML private Label statusLabel;

    private boolean isPasswordVisible = false;

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

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = isPasswordVisible
                ? visiblePasswordField.getText().trim()
                : passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("⚠️ Please fill in all fields.");
            return;
        }

        try {
            if (UserDatabase.validateUser(email, password)) {
                // ✅ Success → load Welcome Screen
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/com/example/emailpasswordapp/ui/welcome_neumorphic.fxml")
                );

                Stage stage = (Stage) emailField.getScene().getWindow();
                Scene scene = new Scene(loader.load());

                // Pass email to WelcomeController
                WelcomeController wc = loader.getController();
                wc.setEmail(email);

                stage.setScene(scene);
                stage.centerOnScreen();

            } else {
                statusLabel.setText("❌ Invalid email or password!");
            }
        } catch (IOException e) {
            statusLabel.setText("⚠️ Error accessing user data!");
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToSignup() throws IOException {
        Stage stage = (Stage) emailField.getScene().getWindow();
        Scene scene = new Scene(FXMLLoader.load(
                getClass().getResource("/com/example/emailpasswordapp/ui/signup_neumorphic.fxml")
        ));
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}
