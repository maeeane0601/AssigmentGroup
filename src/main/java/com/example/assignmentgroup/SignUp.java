package com.example.assignmentgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SignUp implements Initializable {

    @FXML
    private Button btnSignUp;

    @FXML
    private Button btnExit;
    @FXML
    private Label lblMessage;

    @FXML
    private ComboBox<String> myComboBox;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/academic_reporting";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "maeeane";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ComboBox with role options
        myComboBox.getItems().addAll("Admin", "Lecturer", "PRL");
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    @FXML
    void handleSignUp(ActionEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String role = myComboBox.getValue();

        // Validate user input
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || role == null) {
            lblMessage.setText("Please fill all fields.");
            return;
        }
        if (!password.equals(confirmPassword)) {
            lblMessage.setText("Passwords do not match.");
            return;
        }

        // Insert the new user into the database
        if (registerUser(username, password, role)) {
            lblMessage.setText("Sign-Up Successful. You can now log in.");
            lblMessage.setStyle("-fx-text-fill: green;");
        } else {
            lblMessage.setText("Sign-Up!you already have this username");
            lblMessage.setStyle("-fx-text-fill: red;");
        }
    }

    private boolean registerUser(String username, String password, String role) {
        String query = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
            return true; // Sign-up successful
        } catch (SQLException e) {
            e.printStackTrace();
            // Sign-up failed
            return false;
        }
    }
   public void Exit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        try {
            // Navigate back to the Login page
            HelloApplication.nav(stage, "Login.fxml", "Login", 600, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
