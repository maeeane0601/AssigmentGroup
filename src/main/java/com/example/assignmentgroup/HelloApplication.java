
package com.example.assignmentgroup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.nav(stage, "Login.fxml", "Login form", 600, 400);

    }

    public static void nav(Stage stage, String fxml, String title, int V, int V1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), V, V1);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }


    public static void logUserAction(String username) throws IOException {
        try (FileWriter writer = new FileWriter("login-log.txt", true)) {
            writer.write("User: " + username + " logged in at " + LocalDateTime.now() + "\n");
        }
    }

    public static void main(String[] args) {launch();}
}
