package com.example.assignmentgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddAcademicYear {

    @FXML
    private Button btnAddAcademicYear;

    @FXML
    private DatePicker dpSemester1End;

    @FXML
    private DatePicker dpSemester1Start;

    @FXML
    private DatePicker dpSemester2End;

    @FXML
    private DatePicker dpSemester2Start;

    @FXML
    private Label lblEndYear;

    @FXML
    private Label lblSemester1End;

    @FXML
    private Label lblSemester1Start;

    @FXML
    private Label lblSemester2End;

    @FXML
    private Label lblSemester2Start;

    @FXML
    private Label lblStartYear;

    @FXML
    private TextField txtEndYear;

    @FXML
    private TextField txtStartYear;
    @FXML
    private Button btnExit;


    @FXML
    void AddAcademicYear(ActionEvent event) {
        int  start_year= Integer.parseInt(txtStartYear.getText());
        int end_year= Integer.parseInt(txtEndYear.getText());
        Date semester1_start= java.sql.Date.valueOf(dpSemester1Start.getValue());
        Date semester1_end=java.sql.Date.valueOf(dpSemester1End.getValue());
        Date  semester2_start= java.sql.Date.valueOf(dpSemester2Start.getValue());
        Date  semester2_end=java.sql.Date.valueOf(dpSemester2End.getValue());


        // Print each input field's content to the console and insert into database
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO academicyear (start_year, end_year, semester1_start, semester1_end, semester2_start, semester2_end) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, String.valueOf(start_year));
            pstmt.setString(2, String.valueOf(end_year));
            pstmt.setDate(3,semester1_start);
            pstmt.setDate(4,semester1_end );
            pstmt.setDate(5,semester2_start);
            pstmt.setDate(6, semester2_end);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new academic year was inserted successfully!");
            }
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
