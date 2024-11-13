package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeLogin extends Application {

    private static Stage mainStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainStage = primaryStage;
        display();
    }

    public static void display() {
        Stage stage = new Stage();
        stage.setTitle("Employee Login");

        Label loginLabel = new Label("Login ID:");
        TextField loginField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField(); // Using PasswordField for password input
        Button loginButton = new Button("Login");
        Button registerEmployeeButton = new Button("Register Employee"); // New button for registering an employee

        loginButton.setOnAction(event -> {
            String loginId = loginField.getText();
            String password = passwordField.getText();
            if (validateEmployee(loginId, password)) {
                EmployeeHome.display(loginId);
                stage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid Credentials", "Invalid login credentials.");
            }
        });

        registerEmployeeButton.setOnAction(event -> {
            RegisterEmployeeGUI.display(); // Display the employee registration GUI
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(loginLabel, loginField, passwordLabel, passwordField, loginButton,
                registerEmployeeButton);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(200);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    protected static boolean validateEmployee(String loginId, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\employees.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 2 && data[0].trim().equals(loginId) && data[1].trim().equals(password)) {
                    return true; // Login successful
                }
            }
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Error reading employees.csv: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Login failed
    }

    // Add this method to display alerts
    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
