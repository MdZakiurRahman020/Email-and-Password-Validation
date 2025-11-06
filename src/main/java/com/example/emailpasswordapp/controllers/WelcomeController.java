package com.example.emailpasswordapp.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class WelcomeController {

    @FXML
    private Label emailLabel;

    @FXML
    private Label statusLabel;

    /**
     * Called after successful login to set user info.
     */
    public void setEmail(String email) {
        emailLabel.setText("Logged in as: " + email);
    }

    /**
     * Handles user logout — returns to login screen.
     */
    @FXML
    private void handleLogout() {
        statusLabel.setVisible(true);
        statusLabel.setText("Logging out...");

        // Slight delay for smoother transition
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                Platform.runLater(() -> {
                    try {
                        Stage stage = (Stage) emailLabel.getScene().getWindow();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                                "/com/example/emailpasswordapp/ui/login_neumorphic.fxml"
                        ));

                        if (loader.getLocation() == null) {
                            System.err.println("❌ Could not find login_neumorphic.fxml — check your resource path!");
                            statusLabel.setText("⚠️ Login screen missing!");
                            return;
                        }

                        Scene scene = new Scene(loader.load());
                        stage.setScene(scene);
                        stage.centerOnScreen();

                    } catch (IOException e) {
                        statusLabel.setText("⚠️ Error while logging out!");
                        e.printStackTrace();
                    }
                });
            } catch (InterruptedException ignored) {}
        }).start();
    }
}
