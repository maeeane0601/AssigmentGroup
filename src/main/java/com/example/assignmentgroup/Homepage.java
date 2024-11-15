package com.example.assignmentgroup;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Homepage {

    public Button btnlecturerReportingButton;
    @FXML
    private Button btnAdminActions;

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnPRLReporting;

    private String role;

    public void setRole(String role) {
        this.role = role;
        configureButtons();
    }

    private void configureButtons() {
        switch (role) {
            case "Admin":
                // Admin has access to all buttons
                btnAdminActions.setDisable(false);
                btnlecturerReportingButton.setDisable(false);
                btnPRLReporting.setDisable(false);
                break;
            case "Lecturer":
                // Lecturer only has access to Lecturer Reporting
                btnAdminActions.setDisable(true);
                btnlecturerReportingButton.setDisable(false);
                btnPRLReporting.setDisable(true);
                break;
            case "PRL":
                // PRL only has access to PRL Reporting
                btnAdminActions.setDisable(true);
                btnlecturerReportingButton.setDisable(true);
                btnPRLReporting.setDisable(false);
                break;
            default:
                // Unknown role, disable all buttons
                btnAdminActions.setDisable(true);
                btnlecturerReportingButton.setDisable(true);
                btnPRLReporting.setDisable(true);
                break;
        }
    }

    @FXML
    private void AdminActions() throws IOException {
        if ("Admin".equals(role)) {
            HelloApplication.nav((Stage) btnAdminActions.getScene().getWindow(), "AdminActions.fxml", "Admin Home", 623, 553);
        }
    }

    @FXML
    private void lecturerReportingButton() throws IOException {
        if ("Lecturer".equals(role)) {
            HelloApplication.nav((Stage)btnlecturerReportingButton.getScene().getWindow(), "LecturerWeeklyReport.fxml", "Lecturer Weekly Report", 668, 733);
        }
    }

    @FXML
    private void PRLReporting() throws IOException {
        if ("PRL".equals(role)) {
            HelloApplication.nav((Stage) btnPRLReporting.getScene().getWindow(), "PRLWeeklyCourseReport.fxml", "PRL Weekly Course Reporting", 796, 758);
        }
    }

    @FXML
    public void Cancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        try {
            // Navigate back to the HomePage
            HelloApplication.nav(stage, "Login.fxml", "Login", 600, 400);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
