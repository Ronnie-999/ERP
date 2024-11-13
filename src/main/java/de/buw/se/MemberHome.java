package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MemberHome {
    public static void display(String loginId) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\member.csv"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(loginId)) {
                    StringBuilder userDetails = new StringBuilder();
                    userDetails.append("User ID: ").append(parts[0])
                            .append("\nCompany Name: ").append(parts[2])
                            .append("\nPhone Number: ").append(parts[3])
                            .append("\n\nNews:\n");

                    Stage stage = new Stage();
                    stage.setTitle("User Details");

                    VBox vbox = new VBox(10);
                    Label detailsLabel = new Label(userDetails.toString());
                    displayNews(detailsLabel);
                    Button querySupportButton = new Button("Query and Support");
                    querySupportButton.setOnAction(event -> QueryAndSupport.display(loginId));
                    Button feedbackButton = new Button("Feedback");
                    feedbackButton.setOnAction(event -> AddMemberFeedback.display(loginId));
                    Button reviewButton = new Button("Review");
                    reviewButton.setOnAction(event -> AddMemberReview.display(loginId));
                    Button myQueryButton = new Button("My Queries");
                    myQueryButton.setOnAction(event -> MyQueries.display(loginId));
                    Button updateProfileButton = new Button("Update Profile"); // New Update Profile Button
                    updateProfileButton.setOnAction(event -> UpdateProfile.display(loginId));

                    vbox.getChildren().addAll(detailsLabel, querySupportButton, feedbackButton, reviewButton, myQueryButton, updateProfileButton);

                    Scene scene = new Scene(vbox, 300, 400);
                    stage.setScene(scene);
                    stage.show();
                    found = true;
                    break;
                }
            }
            if (!found) {
                UtilClass.showAlert(Alert.AlertType.WARNING, "User Details", "User ID not found or associated company details not available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayNews(Label detailsLabel) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\news.csv"))) {
            String line;
            int count = 1;
            StringBuilder newsContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                newsContent.append(count).append(". ").append(line).append("\n");
                count++;
            }
            detailsLabel.setText(detailsLabel.getText() + newsContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}