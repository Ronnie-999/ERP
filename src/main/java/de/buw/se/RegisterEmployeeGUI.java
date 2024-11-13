package de.buw.se;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterEmployeeGUI {

    // Path to the employee credentials CSV file
    private static final String EMPLOYEES_CSV_PATH = "src\\main\\resources\\employees.csv";

    public static void display() {
        Stage stage = new Stage();
        stage.setTitle("Register Employee");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Label employeeIdLabel = new Label("Employee ID:");
        TextField employeeIdField = new TextField();
        Label phoneNumberLabel = new Label("Phone Number:");
        TextField phoneNumberField = new TextField();
        Button registerButton = new Button("Register");

        registerButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            String employeeId = employeeIdField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || employeeId.isEmpty() || phoneNumber.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "All fields are required and cannot be empty.");
            } else {
                if (isDuplicateEmployee(username, password, employeeId, phoneNumber, EMPLOYEES_CSV_PATH)) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Employee with the same username, password, Employee ID, or phone number already exists.");
                } else {
                    saveEmployeeCredentials(username, password, employeeId, phoneNumber, EMPLOYEES_CSV_PATH);
                    showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "Employee registered successfully.");
                    stage.close();
                }
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, employeeIdLabel,
                employeeIdField, phoneNumberLabel, phoneNumberField, registerButton);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(250);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    protected static boolean isDuplicateEmployee(String username, String password, String employeeId, String phoneNumber, String csv_path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(csv_path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 4) {
                    if (data[0].trim().equals(username)) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Username already exists.");
                        return true;
                    }
                    if (data[1].trim().equals(password)) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Password already exists.");
                        return true;
                    }
                    if (data[2].trim().equals(employeeId)) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Employee ID already exists.");
                        return true;
                    }
                    if (data[3].trim().equals(phoneNumber)) {
                        showAlert(Alert.AlertType.ERROR, "Error", "Phone number already exists.");
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to read employee credentials: " + e.getMessage());
        }
        return false;
    }

    protected static void saveEmployeeCredentials(String username, String password, String employeeId,
            String phoneNumber, String csv_path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csv_path, true))) {
            writer.write(username + "," + password + "," + employeeId + "," + phoneNumber);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save employee credentials: " + e.getMessage());
        }
    }

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
