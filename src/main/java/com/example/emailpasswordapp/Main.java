package com.example.emailpasswordapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry point of the Neumorphic Email-Password Validation App.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/emailpasswordapp/ui/login_neumorphic.fxml")
            );


            Scene scene = new Scene(loader.load());
            stage.setTitle("Neumorphic Login System");
            stage.setScene(scene);
            stage.setResizable(false); // Keep the UI proportions consistent
            stage.show();

        } catch (Exception e) {
            System.err.println("Error loading initial UI: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
