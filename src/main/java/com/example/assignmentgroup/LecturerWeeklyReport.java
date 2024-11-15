package com.example.assignmentgroup;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LecturerWeeklyReport implements Initializable {
    @FXML
    private Button btnLogout;

    @FXML
    private Button btnSubmitLecturerReport;

    @FXML
    private TextField txtCourseName;

    @FXML
    private TextField txtAdditionalNotes;

    @FXML
    private TextField txtChallengesFaced;

    @FXML
    private TextField txtStudentParticipation;

    @FXML
    private TextField txtTopicsCovered;

    @FXML
    private TextField txtWeekNumber;

    @FXML
    private TableView<Student> tblAttendance;
    @FXML
    private TableColumn<Student, String> colstudent_id;
    @FXML
    private TableColumn<Student, String> colname;
    @FXML
    private TableColumn<Student, String> colPresent;

    private ObservableList<Student> studentList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeTableColumns();
        populateStudentData();
    }

    private void initializeTableColumns() {
        colstudent_id.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStudent_id()));
        colname.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        // Custom cell factory for the attendance column to use a ComboBox
        colPresent.setCellFactory(column -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>(FXCollections.observableArrayList("Yes", "No"));

            {
                comboBox.setOnAction(event -> {
                    Student student = getTableView().getItems().get(getIndex());
                    student.setPresent(comboBox.getValue());
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(comboBox);
                    Student student = getTableView().getItems().get(getIndex());
                    comboBox.setValue(student.getPresent());
                }
            }
        });
    }

    private void populateStudentData() {
        if (tblAttendance == null) {
            System.out.println("Error: TableView is null!");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT student_id, name FROM student";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            studentList.clear();
            while (rs.next()) {
                String studentID = rs.getString("student_id");
                String name = rs.getString("name");
                // Default attendance to "No" initially
                studentList.add(new Student(studentID, name, "No"));
            }
            tblAttendance.setItems(studentList);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void SubmitLecturerReport(ActionEvent event) {
        String courseName = txtCourseName.getText();
        String weekNumberText = txtWeekNumber.getText();
        String topicsCovered = txtTopicsCovered.getText();
        String studentParticipation = txtStudentParticipation.getText();
        String challengesFaced = txtChallengesFaced.getText();
        String additionalNotes = txtAdditionalNotes.getText();

        // Input validation
        if (courseName.isEmpty() || weekNumberText.isEmpty() || topicsCovered.isEmpty()) {
            showAlert("Input Error", "Please fill in all required fields.");
            return;
        }

        int weekNumber;
        try {
            weekNumber = Integer.parseInt(weekNumberText);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Week Number must be a number.");
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO lecturerweeklyreport (course_name, week_number, topics_covered, student_participation, challenges_faced, additional_notes, student_id, student_name, present) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);

            for (Student student : studentList) {
                pstmt.setString(1, courseName);
                pstmt.setInt(2, weekNumber);
                pstmt.setString(3, topicsCovered);
                pstmt.setString(4, studentParticipation);
                pstmt.setString(5, challengesFaced);
                pstmt.setString(6, additionalNotes);
                pstmt.setString(7, student.getStudent_id());
                pstmt.setString(8, student.getName());
                pstmt.setString(9, student.getPresent());

                // Add to batch for batch execution
                pstmt.addBatch();
            }
            // Execute batch insert
            pstmt.executeBatch();
            showAlert("Success", "Lecturer weekly report submitted successfully!");
            clearForm();

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Database Error", "Failed to submit the report.");
        }
    }

    private void clearForm() {
        txtCourseName.clear();
        txtWeekNumber.clear();
        txtTopicsCovered.clear();
        txtStudentParticipation.clear();
        txtChallengesFaced.clear();
        txtAdditionalNotes.clear();
        studentList.forEach(student -> student.setPresent("No")); // Reset attendance
        tblAttendance.refresh();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
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
