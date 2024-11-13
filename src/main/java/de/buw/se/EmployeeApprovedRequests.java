package de.buw.se;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeApprovedRequests {
    public static void display() {
        List<String[]> pendingEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\pending.csv"))) {
            String line;
            int count = 1;
            while ((line = reader.readLine()) != null) {
                String[] entry = line.split(",");
                for(String str: entry) {
                    System.out.println(str);
                }
                System.out.println();
                pendingEntries.add(entry);
                count++;
            }
            

            if (pendingEntries.isEmpty()) {
                UtilClass.showAlert(Alert.AlertType.INFORMATION, "No Pending Requests", "No pending requests.");
                return;
            }

            Stage stage = new Stage();
            stage.setTitle("Approve Requests");

            VBox vbox = new VBox(10);
            Label label = new Label("Pending Requests:");
            vbox.getChildren().add(label);

            for (int i = 0; i < pendingEntries.size(); i++) {
                String[] entry = pendingEntries.get(i);
                Label entryLabel = new Label((i + 1) + ". ID: " + entry[0] + ", Company: " + entry[2] + ", Phone: " + entry[3]);
                vbox.getChildren().add(entryLabel);
            }

            ChoiceBox<String> choiceBox = new ChoiceBox<>();
            for (int i = 0; i < pendingEntries.size(); i++) {
                choiceBox.getItems().add(String.valueOf(i + 1));
            }
            choiceBox.setValue("1"); // Set default value

            Button approveButton = new Button("Approve");
            approveButton.setOnAction(event -> {
                int selectedIndex = choiceBox.getSelectionModel().getSelectedIndex();
                if (selectedIndex != -1) {
                    String[] approvedEntry = pendingEntries.get(selectedIndex);
                    addApprovedEntry(approvedEntry);
                    UtilClass.showAlert(Alert.AlertType.INFORMATION, "Request Approved", "Request approved.");
                    stage.close();
                } else {
                    UtilClass.showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a request to approve.");
                }
            });

            vbox.getChildren().addAll(choiceBox, approveButton);

            Scene scene = new Scene(vbox);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Failed to read pending requests: " + e.getMessage());
        }
    }
    private static void addApprovedEntry(String[] entry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\member.csv", true))) {
            StringBuilder entryBuilder = new StringBuilder();
            for (String field : entry) {
                entryBuilder.append(field).append(","); // Append each field followed by a comma
            }
            entryBuilder.deleteCharAt(entryBuilder.length() - 1); // Remove the last comma
            entryBuilder.append(System.lineSeparator()); // Add a newline character
            writer.write(entryBuilder.toString());
        } catch (IOException e) {
            e.printStackTrace();
            UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Failed to add approved entry: " + e.getMessage());
            return; // Exit method if writing to member.csv fails
        }

        // Remove the approved entry from pending.csv
        try {
            List<String> lines = Files.readAllLines(Paths.get("src/main/resources/pending.csv"));
            List<String> updatedLines = new ArrayList<>();
            for (String line : lines) {
                if (!line.equals(String.join(",", entry))) {
                    updatedLines.add(line);
                }
            }
            Files.write(Paths.get("src/main/resources/pending.csv"), updatedLines);
        } catch (IOException e) {
            e.printStackTrace();
            UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Failed to update pending.csv: " + e.getMessage());
        }
    }
}
