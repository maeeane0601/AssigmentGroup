package com.example.assignmentgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSemester implements Initializable {

    @FXML
    private Button btnAddSemester;
    @FXML
    private Button btnExit;

    @FXML
    private Label lblSemesterName;

    @FXML
    private ComboBox<String> myComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populate ComboBox with semester options
        myComboBox.getItems().addAll("Semester1","Semester2");
    }


    @FXML
   public void AddSemester(ActionEvent event) {
        String Semester_name=myComboBox.getValue();
        // Print each TextField's content to the console and insert into database
        try (Connection conn= DatabaseConnection.getConnection()) {
            String query = "INSERT INTO semester (semester_name) VALUES (?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,Semester_name);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new semester was inserted successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void Exit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        try {
            // Navigate back to the Login page
            HelloApplication.nav(stage, "AdminActions.fxml", "AdminActions", 623, 553);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
