
package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MemberLogin {

    private static String currentLoggedInUser;

    public static String getCurrentLoggedInUser() {
        return currentLoggedInUser;
    }

    public static void setCurrentLoggedInUser(String currentLoggedInUser) {
        MemberLogin.currentLoggedInUser = currentLoggedInUser;
    }

    public static void display() {
        Stage stage = new Stage();
        stage.setTitle("Member Login");

        Label loginLabel = new Label("Login ID:");
        TextField loginField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register Company");

        loginButton.setOnAction(event -> {
            String loginId = loginField.getText();
            String password = passwordField.getText();
            if (validateMember(loginId, password)) {
                MemberLogin.setCurrentLoggedInUser(loginId);
                MemberHome.display(loginId);
                stage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid Credentials",
                        "Invalid login credentials or credentials not approved by administrator yet.");
            }
        });

        registerButton.setOnAction(event -> {
            RegisterCompany.display();
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(loginLabel, loginField, passwordLabel, passwordField, loginButton, registerButton);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(250);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    protected static boolean validateMember(String loginId, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\member.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String fileLoginId = parts[0].trim();
                String filePassword = parts[1].trim();
                if (fileLoginId.equals(loginId) && filePassword.equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


