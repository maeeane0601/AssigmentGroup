package com.example.assignmentgroup;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class View implements Initializable {
    @FXML
    private Button btnCancel;

    @FXML
    private TableColumn<ViewData, String> collectures;
    @FXML
    private TableColumn<ViewData, String> colmodules;
    @FXML
    private TableColumn<ViewData, String> colsemesters;
    @FXML
    private TableView<ViewData> tblView;

    private ObservableList<ViewData> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up the columns
        collectures.setCellValueFactory(new PropertyValueFactory<>("lecturerName"));
        colmodules.setCellValueFactory(new PropertyValueFactory<>("moduleName"));
        colsemesters.setCellValueFactory(new PropertyValueFactory<>("semesterName"));

        // Load data into the TableView
        loadData();
    }

    private void loadData() {
        // Clear the existing data
        dataList.clear();

        try (Connection conn = DatabaseConnection.getConnection()) {
            // Query for lecturer names
            String lecturerQuery = "SELECT name FROM lecturer";
            try (PreparedStatement stmt = conn.prepareStatement(lecturerQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dataList.add(new ViewData(rs.getString("name"), null, null));
                }
            }

            // Query for module names
            String moduleQuery = "SELECT module_name FROM module";
            try (PreparedStatement stmt = conn.prepareStatement(moduleQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dataList.add(new ViewData(null, rs.getString("module_name"), null));
                }
            }

            // Query for semester names
            String semesterQuery = "SELECT semester_name FROM semester";
            try (PreparedStatement stmt = conn.prepareStatement(semesterQuery);
                 ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    dataList.add(new ViewData(null, null, rs.getString("semester_name")));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the data to the TableView
        tblView.setItems(dataList);
    }

    public void Cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        try {
            // Navigate back to the Login page
            HelloApplication.nav(stage, "AdminActions.fxml", "Admin", 623, 553);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

