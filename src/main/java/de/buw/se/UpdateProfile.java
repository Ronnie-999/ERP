package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UpdateProfile {
    private static final String MEMBER_CSVFILE_PATH = "src\\main\\resources\\member.csv";

    public static void display(String loginId) {
        Stage stage = new Stage();
        stage.setTitle("Update Profile");

        Label loginIdLabel = new Label("New Login ID:");
        TextField loginIdField = new TextField();
        Label passwordLabel = new Label("New Password:");
        PasswordField passwordField = new PasswordField();
        Label phoneNumberLabel = new Label("New Phone Number:");
        TextField phoneNumberField = new TextField();
        Label companyNameLabel = new Label("New Company Name:");
        TextField companyNameField = new TextField();

        Button updateButton = new Button("Update");

        updateButton.setOnAction(event -> {
            String newLoginId = loginIdField.getText().trim();
            String newPassword = passwordField.getText().trim();
            String newPhoneNumber = phoneNumberField.getText().trim();
            String newCompanyName = companyNameField.getText().trim();
            if (updateProfile(loginId, newLoginId, newPassword, newPhoneNumber, newCompanyName)) {
                UtilClass.showAlert(Alert.AlertType.INFORMATION, "Profile Updated", "Profile information updated successfully.");
                MemberLogin.setCurrentLoggedInUser(newLoginId);
                stage.close();
            } else {
                UtilClass.showAlert(Alert.AlertType.ERROR, "Update Failed", "Failed to update profile information.");
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(loginIdLabel, loginIdField, passwordLabel, passwordField, phoneNumberLabel, phoneNumberField, companyNameLabel, companyNameField, updateButton);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(250);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private static boolean updateProfile(String oldLoginId, String newLoginId, String newPassword, String newPhoneNumber, String newCompanyName) {
        List<String[]> memberData = new ArrayList<>();
        boolean updated = false;

        try (BufferedReader br = new BufferedReader(new FileReader(MEMBER_CSVFILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(oldLoginId)) {
                    parts[0] = newLoginId;
                    parts[1] = newPassword;
                    parts[2] = newCompanyName;
                    parts[3] = newPhoneNumber;
                    updated = true;
                }
                memberData.add(parts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (updated) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(MEMBER_CSVFILE_PATH))) {
                for (String[] parts : memberData) {
                    pw.println(String.join(",", parts));
                }
                return true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
