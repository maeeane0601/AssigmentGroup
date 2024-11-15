package com.example.assignmentgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewClass implements Initializable {

    @FXML
    private Button btnAddClass;

    @FXML
    private Button btnExit;

    @FXML
    private Label lblClassName;

    @FXML
    private Label lblSemester;

    @FXML
    private TextField txtClassName;

    @FXML
    private ComboBox<String> myComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ComboBox with semester options
        myComboBox.getItems().addAll("Semester1", "Semester2");
    }

    @FXML
    public void AddClass(ActionEvent event) {
        String class_name = txtClassName.getText();
        String semester = myComboBox.getValue();

        // Insert new class into the database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO class (class_name, semester) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, class_name);
            pstmt.setString(2, semester);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new class was inserted successfully!");

                // Retrieve all class data after insertion and display it in an alert
                displayAllClasses(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayAllClasses(Connection conn) {
        StringBuilder allClasses = new StringBuilder("Current Classes:\n");

        // Query to retrieve all classes
        String query = "SELECT class_name, semester FROM class";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            // Loop through the results and append each class to the StringBuilder
            while (rs.next()) {
                String className = rs.getString("class_name");
                String semester = rs.getString("semester");
                allClasses.append("Class: ").append(className)
                        .append(", Semester: ").append(semester).append("\n");
            }

            // Display the results in an alert box
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Class List");
            alert.setHeaderText(null);
            alert.setContentText(allClasses.toString());
            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Exit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        try {
            // Navigate back to the AdminActions page
            HelloApplication.nav(stage, "AdminActions.fxml", "AdminActions", 623, 553);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
