package com.example.assignmentgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddStudent {

    @FXML
    private Button btnAddStudent;

    @FXML
    private Button btnExit;

    @FXML
    private Label lblClassName;

    @FXML
    private Label lblCourse;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPhoneNumbers;

    @FXML
    private Label lblStudentID;

    @FXML
    private Label lblSurname;

    @FXML
    private TextField txtClassName;

    @FXML
    private TextField txtCourse;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhoneNumbers;

    @FXML
    private TextField txtStudentID;

    @FXML
    private TextField txtSurname;

    @FXML
    public void AddStudent(ActionEvent event) {
        String student_id = txtStudentID.getText();
        String name = txtName.getText();
        String surname = txtSurname.getText();
        String email = txtEmail.getText();
        String phone_number = txtPhoneNumbers.getText();
        String class_name = txtClassName.getText();
        String course = txtCourse.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO student (student_id, name, surname, email, phone_number, class_name, course) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, student_id);
            pstmt.setString(2, name);
            pstmt.setString(3, surname);
            pstmt.setString(4, email);
            pstmt.setString(5, phone_number);
            pstmt.setString(6, class_name);
            pstmt.setString(7, course);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                // Show a confirmation alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Student Added");
                alert.setHeaderText(null);
                alert.setContentText("A new student was inserted successfully!");
                alert.showAndWait();

                // Display all students after insertion
                displayAllStudents(conn);
            }

            // Clear input fields
            txtStudentID.clear();
            txtName.clear();
            txtSurname.clear();
            txtEmail.clear();
            txtPhoneNumbers.clear();
            txtClassName.clear();
            txtCourse.clear();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayAllStudents(Connection conn) {
        StringBuilder allStudents = new StringBuilder("Current Students:\n");

        String query = "SELECT student_id, name, surname, email, phone_number, class_name, course FROM student";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Append each student record to the StringBuilder
            while (rs.next()) {
                String studentId = rs.getString("student_id");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String className = rs.getString("class_name");
                String course = rs.getString("course");

                allStudents.append("ID: ").append(studentId)
                        .append(", Name: ").append(name)
                        .append(" ").append(surname)
                        .append(", Email: ").append(email)
                        .append(", Phone: ").append(phoneNumber)
                        .append(", Class: ").append(className)
                        .append(", Course: ").append(course)
                        .append("\n");
            }

            // Display all student records in an alert box
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Student List");
            alert.setHeaderText("All Students in the Database");
            alert.setContentText(allStudents.toString());
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Exit(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        try {
            // Navigate back to the Login page
            HelloApplication.nav(stage, "AdminActions.fxml", "AdminActions", 623, 553);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
