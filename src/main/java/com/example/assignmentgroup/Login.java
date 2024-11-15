package com.example.assignmentgroup;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Login {
    @FXML
    private Button btnSignUp;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Label lblUsername;

    @FXML
    private Label lblPassword;

    @FXML
    private TextField txtInvalidPassword;

    private Connection connect() {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/academic_reporting";
        String user = "root";
        String password = "maeeane";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    @FXML
    public void Login() throws IOException {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Check if default users exist in the database; if not, add them
        initializeDefaultUsers();

        // Validate login credentials
        if (isValidCredentials(username, password)) {
            AdminActions.setLoggedInUsername(username);
            HelloApplication.logUserAction(username);
            String role = getUserRole(username);

            // Pass the role to the HomePage and navigate there
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Scene scene = new Scene(loader.load());
            Homepage controller = loader.getController();
            controller.setRole(role);  // Set the role in the HomePageController

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Home Page");
            stage.show();
        } else {
            txtInvalidPassword.setText("Invalid username or password");
        }
    }

    // Initialize default users if they don't exist
    private void initializeDefaultUsers() {
        registerUserIfNotExists("Jabu", "Maeeane", "Admin");
        registerUserIfNotExists("Lecturer", "1111", "Lecturer");
        registerUserIfNotExists("PRL", "2222", "PRL");
    }

    // Method to register a user if they are not already in the database
    private void registerUserIfNotExists(String username, String password, String role) {
        if (!userExists(username)) {
            registerUser(username, password, role);
        }
    }

    private boolean userExists(String username) {
        String query = "SELECT 1 FROM user WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // returns true if a match is found
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean isValidCredentials(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // returns true if a match is found
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getUserRole(String username) {
        String query = "SELECT role FROM user WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    private void registerUser(String username, String password, String role) {
        String query = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtUsername.clear();
        txtPassword.clear();
    }

    @FXML
    public void handleSignUp() throws IOException {
        // Load the Sign-Up form
        FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(loader.load());

        // Set up the new stage for Sign-Up
        Stage stage = (Stage) btnSignUp.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Sign Up");
        stage.show();
    }

}
