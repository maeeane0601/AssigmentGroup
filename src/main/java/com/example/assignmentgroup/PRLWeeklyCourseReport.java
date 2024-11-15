package com.example.assignmentgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PRLWeeklyCourseReport {
    @FXML
    private Button btnLogout;

    @FXML
    private Button btnSubmitPRLReport;

    @FXML
    private Label lblChapterCovered;

    @FXML
    private Label lblClass;

    @FXML
    private Label lblCourseName;

    @FXML
    private Label lblLecturer;

    @FXML
    private Label lblModeofDelivery;

    @FXML
    private TextField txtChalenges;

    @FXML
    private TextField txtChapterCovered;

    @FXML
    private TextField txtClass;

    @FXML
    private TextField txtCourseName;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtLevelOfAttendance;

    @FXML
    private TextField txtMissedClasses;

    @FXML
    private TextField txtModeofDelivery;

    @FXML
    private TextField txtRecommendations;

    @FXML
    private TextField txtStudentName;

    @FXML
    private TextField txtStudentRegistration;

    @FXML
    private TextField txtmalpractice;

    @FXML
public  void SubmitPRLReport(ActionEvent event) {
        String lecturer=txtLecturer.getText();
        String class_name=txtClass.getText();
        String course_name=txtCourseName.getText();
        String chapter_covered=txtChapterCovered.getText();
        String mode_of_delivery=txtModeofDelivery.getText();
        String challenges=txtChalenges.getText();
        String  recommendations=txtRecommendations.getText();
        String  student_name=txtStudentName.getText();
        String  level_of_attendance=txtLevelOfAttendance.getText();
        int missed_classes= Integer.parseInt(txtMissedClasses.getText());
        String student_registration=txtStudentRegistration.getText();
        String  malpractice=txtmalpractice.getText();

        // Print each TextField's content and insert into database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO prlweeklycoursereport (lecturer, class_name, course_name, chapter_covered, mode_of_delivery, challenges, recommendations, student_name, level_of_attendance, missed_classes, student_registration, malpractice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,lecturer );
            pstmt.setString(2,class_name );
            pstmt.setString(3, course_name);
            pstmt.setString(4,chapter_covered );
            pstmt.setString(5,mode_of_delivery );
            pstmt.setString(6, challenges);
            pstmt.setString(7, recommendations);
            pstmt.setString(8,student_name );
            pstmt.setString(9, level_of_attendance);
            pstmt.setString(10, String.valueOf(missed_classes));
            pstmt.setString(11,student_registration );
            pstmt.setString(12,malpractice );

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new Principal weekly Course Report was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void Logout(ActionEvent event) {
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        try {
            // Navigate back to the Login page
            HelloApplication.nav(stage, "Login.fxml", "Login", 600, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
