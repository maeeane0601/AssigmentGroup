package com.example.assignmentgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddLecturer implements Initializable {

    @FXML
    private Button btnAddLecturer;

    @FXML
    private TextField txtEmployeeNumber;

    @FXML
    private TextField txtName;

    @FXML
    private ComboBox<String> myComboBox;

    @FXML
    private ComboBox<String> myCombo;

    @FXML
    private ComboBox<String> AssignAcademicYearBox;

    @FXML
    private ComboBox<String> AssignClassBox;

    @FXML
    private ComboBox<String> AssignSemesterBox;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhoneNumber;

    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ComboBox with role options
        myComboBox.getItems().addAll("Lecturer", "Tutor", "Year Leader");

        // Load module names into myCombo
        loadModuleNames();

        // Load class names into AssignClassBox
        loadClassNames();

        // Load academic years into AssignAcademicYearBox
        loadAcademicYears();

        // Load semester names into AssignSemesterBox
        loadSemesterNames();
    }

    private void loadModuleNames() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT module_name FROM module";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String moduleName = rs.getString("module_name");
                myCombo.getItems().add(moduleName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadClassNames() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT class_name FROM class";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String className = rs.getString("class_name");
                AssignClassBox.getItems().add(className);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadAcademicYears() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT start_year, end_year FROM academicyear";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String startYear = rs.getString("start_year");
                String endYear = rs.getString("end_year");
                String academicYear = startYear + " - " + endYear;
                AssignAcademicYearBox.getItems().add(academicYear);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadSemesterNames() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT semester_name FROM semester";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String semesterName = rs.getString("semester_name");
                AssignSemesterBox.getItems().add(semesterName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void AddLecturer(ActionEvent event) {
        String employeeNumber = txtEmployeeNumber.getText();
        String name = txtName.getText();
        String role = myComboBox.getValue();
        String email = txtEmail.getText();
        String phoneNumber = txtPhoneNumber.getText();
        String moduleName = myCombo.getValue();
        String className = AssignClassBox.getValue();
        String academicYear = AssignAcademicYearBox.getValue();
        String semesterName = AssignSemesterBox.getValue();

        // Check if any field is empty
        if (employeeNumber.isEmpty() || name.isEmpty() || role == null || email.isEmpty() || phoneNumber.isEmpty()
                || moduleName == null || className == null || academicYear == null || semesterName == null) {

            // Show alert if any required field is empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incomplete Information");
            alert.setHeaderText("Please fill in all required fields");
            alert.setContentText("Ensure that all fields are filled before adding a lecturer.");
            alert.showAndWait();
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Lecturer (employee_number, name, role, email, phone_number, module_name, class_name, academic_year, semester_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, employeeNumber);
            pstmt.setString(2, name);
            pstmt.setString(3, role);
            pstmt.setString(4, email);
            pstmt.setString(5, phoneNumber);
            pstmt.setString(6, moduleName);
            pstmt.setString(7, className);
            pstmt.setString(8, academicYear);
            pstmt.setString(9, semesterName);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new lecturer was inserted successfully!");
            }

            // Clear input fields after successful insertion
            txtEmployeeNumber.clear();
            txtName.clear();
            txtEmail.clear();
            txtPhoneNumber.clear();
            myComboBox.getSelectionModel().clearSelection();
            myCombo.getSelectionModel().clearSelection();
            AssignClassBox.getSelectionModel().clearSelection();
            AssignAcademicYearBox.getSelectionModel().clearSelection();
            AssignSemesterBox.getSelectionModel().clearSelection();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Exit(ActionEvent actionEvent) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        try {
            HelloApplication.nav(stage, "AdminActions.fxml", "AdminActions", 623, 553);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
