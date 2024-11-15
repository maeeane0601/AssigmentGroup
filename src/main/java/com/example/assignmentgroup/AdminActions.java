package com.example.assignmentgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminActions {

    @FXML
    private Button btnAddAcademicYear;

    @FXML
    private Button btnView;
    @FXML
    private Button btnAddLecturer;
    @FXML
    private Button btnAddModule;
    @FXML
    private Button btnAddSemester;
    @FXML
    private Button btnAddStudent;
    @FXML
    private Button btnLogout;
    @FXML
    private Button btnAddClass;
    @FXML
    private Button btnViewProfile;
    @FXML
    private Label txtAdminActions;
    @FXML
    private TextArea txtViewProfile;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/academic_reporting";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "maeeane";

    // Stores the logged-in username for session management
    private static String loggedInUsername;

    // Set the logged-in username (called from Login class after successful login)
    public static void setLoggedInUsername(String username) {
        loggedInUsername = username;
    }

    // Function to view the profile of the logged-in user
    public void btnViewProfile(ActionEvent event) {
        // Check if there's a logged-in user
        if (loggedInUsername == null || loggedInUsername.isEmpty()) {
            txtViewProfile.setText("No user is logged in.");
            return;
        }

        String query = "SELECT username, password, role FROM user WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, loggedInUsername);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Display user details in txtViewProfile
                String userDetails ="The Admin Profile"+"\n" +"Username: " + rs.getString("username") + "\n"
                        + "Password: " + rs.getString("password") + "\n"
                        + "Role: " + rs.getString("role");

                txtViewProfile.setText(userDetails);
            } else {
                txtViewProfile.setText("User details not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            txtViewProfile.setText("Error retrieving user profile.");
        }
    }

    // Other scene navigation methods
    public void AddAcademicYear(ActionEvent event) {
        try {
            // Load AddAcademicYear.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAcademicYear.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add Academic year Form");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddLecturer(ActionEvent event) {
        try {
            // Load AddLecturer.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddLecturer.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add Lecturer Form");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void AddModule(ActionEvent event){
        try {
            // Load AddModule.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddModule.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add Module Form");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddSemester(ActionEvent event){

        try {
            // Load AddSemester.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddSemester.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add Semester Form");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddStudent(ActionEvent event){
        try {
            // Load AddStudent.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddStudent.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add Student Form");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void AddClass(ActionEvent event) {
        try {
            // Load AddClass.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddNewClass.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Add Class Form");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void View(ActionEvent event) {
        try {
            // Load the View.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("View.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the current stage and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("View Page");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Logout(ActionEvent actionEvent) {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        try {
            // Navigate back to the Login page
            HelloApplication.nav(stage, "Login.fxml", "Login", 600, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
