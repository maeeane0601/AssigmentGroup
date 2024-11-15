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

public class AddModule {

    @FXML
    private Label LblModuleCode;

    @FXML
    private Button btnAddModule;

    @FXML
    private Button btnExit;

    @FXML
    private Label lblClassName;

    @FXML
    private Label lblLecturer;

    @FXML
    private Label lblModuleName;

    @FXML
    private TextField txtClassName;

    @FXML
    private TextField txtLecturer;

    @FXML
    private TextField txtModuleCode;

    @FXML
    private TextField txtModuleName;

    public void AddModule(ActionEvent event) {
        String module_name = txtModuleName.getText();
        String module_code = txtModuleCode.getText();
        String lecturer = txtLecturer.getText();
        String class_name = txtClassName.getText();

        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO module (module_name, module_code, lecturer, class_name) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, module_name);
            pstmt.setString(2, module_code);
            pstmt.setString(3, lecturer);
            pstmt.setString(4, class_name);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                // Show an alert on successful insertion
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Module Added");
                alert.setHeaderText(null);
                alert.setContentText("A new module was inserted successfully!");
                alert.showAndWait();

                // Display all modules after insertion
                displayAllModules(conn);
            }

            // Clear input fields
            txtModuleName.clear();
            txtModuleCode.clear();
            txtLecturer.clear();
            txtClassName.clear();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayAllModules(Connection conn) {
        StringBuilder allModules = new StringBuilder("Current Modules:\n");

        // Query to retrieve all modules
        String query = "SELECT module_name, module_code, lecturer, class_name FROM module";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Loop through the results and append each module to the StringBuilder
            while (rs.next()) {
                String moduleName = rs.getString("module_name");
                String moduleCode = rs.getString("module_code");
                String lecturer = rs.getString("lecturer");
                String className = rs.getString("class_name");
                allModules.append("Module: ").append(moduleName)
                        .append(", Code: ").append(moduleCode)
                        .append(", Lecturer: ").append(lecturer)
                        .append(", Class: ").append(className).append("\n");
            }

            // Display the results in an alert box
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Module List");
            alert.setHeaderText(null);
            alert.setContentText(allModules.toString());
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Exit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        try {
            // Navigate back to the Admin Actions page
            HelloApplication.nav(stage, "AdminActions.fxml", "AdminActions", 623, 553);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
