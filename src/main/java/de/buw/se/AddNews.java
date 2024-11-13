package de.buw.se;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddNews {
    public static void display() {
        Stage stage = new Stage();
        stage.setTitle("Add News");

        VBox vbox = new VBox(10);
        Label label = new Label("Enter the news:");
        TextField newsField = new TextField();
        Button addButton = new Button("Add");

        addButton.setOnAction(event -> {
            String news = newsField.getText();
            if (news.isEmpty()) {
                UtilClass.showAlert(Alert.AlertType.ERROR, "INVALID INPUT", "News cannot be empty.");
            } else {
                boolean success = addNews(news);
                if (success) {
                    UtilClass.showAlert(Alert.AlertType.INFORMATION, "NEWS ADDED", "News added successfully.");
                    stage.close();
                } else {
                    UtilClass.showAlert(Alert.AlertType.ERROR, "FAILED", "Failed to add news.");
                }
            }
        });

        vbox.getChildren().addAll(label, newsField, addButton);

        Scene scene = new Scene(vbox, 300, 200);
        stage.setScene(scene);
        stage.show();
    }

    protected static boolean addNews(String news) {
        if (news.isEmpty()) {
            return false; // Return false if the news string is empty
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\news.csv", true))) {
            writer.write(news); // Write the news to the file
            writer.newLine(); // Move to the next line for the next news
            return true; // Return true if successfully added news
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Return false if failed to add news
        }
    }
}

