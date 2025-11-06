module com.example.emailpasswordapp {
    requires javafx.controls;
    requires javafx.fxml;

    // 👇 This line allows FXML to access your controllers package
    opens com.example.emailpasswordapp.controllers to javafx.fxml;

    // 👇 Optional but good practice — opens main package for app launching
    opens com.example.emailpasswordapp to javafx.graphics, javafx.fxml;

    // 👇 If you use models/utilities with FXML bindings later
    exports com.example.emailpasswordapp.controllers;
    exports com.example.emailpasswordapp.models;
    exports com.example.emailpasswordapp.utils;
}
