package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CompanySearch {

    public static void display() {
        if (MemberLogin.getCurrentLoggedInUser() == null) {
            UtilClass.showAlert(Alert.AlertType.WARNING, "Authentication Required", "Please log in to access this feature.");
            MemberLogin.display();
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Company Search");

        Label nameLabel = new Label("Enter the name of the company to search:");
        TextField nameField = new TextField();
        Button searchButton = new Button("Search");

        searchButton.setOnAction(event -> {
            String partialCompanyName = nameField.getText().trim();
            searchCompany(partialCompanyName, stage); // Pass the stage to close after search
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(nameLabel, nameField, searchButton);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(200);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private static void searchCompany(String partialCompanyName, Stage stage) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\member.csv"));

            List<String[]> matchingCompanies = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 3 && data[2].toLowerCase().contains(partialCompanyName.toLowerCase())) {
                    matchingCompanies.add(data);
                }
            }

            if (!matchingCompanies.isEmpty()) {
                StringBuilder companiesFound = new StringBuilder();
                for (String[] company : matchingCompanies) {
                    companiesFound.append("Name: ").append(company[2]).append("\nPhone: ").append(company[3]).append("\n\n");
                }
                UtilClass.showAlert(Alert.AlertType.INFORMATION, "Companies Found", companiesFound.toString());
            } else {
                UtilClass.showAlert(Alert.AlertType.INFORMATION, "No Companies Found", "No companies found matching the search criteria.");
            }

            reader.close(); // Close the file reader
        } catch (IOException e) {
            UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Error reading member.csv: " + e.getMessage());
            e.printStackTrace();
        }

        // Close the stage after search
        stage.close();
    }
}

