package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeHome {

    public static final String FEEDBACK_FILE_PATH = "src\\main\\resources\\feedback.csv";
    public static final String REVIEW_FILE_PATH = "src\\main\\resources\\review.csv";

    public static void display(String loggedInEmployee) {

        Stage stage = new Stage();
        stage.setTitle("Employee Functionalities");

        Button approveRequestsButton = new Button("Approve Requests");
        Button addNewsButton = new Button("Add News");
        Button answerQuery = new Button("Answer Query");
        Button viewFeedbackButton = new Button("View Feedback");
        Button viewReviewButton = new Button("View Review");

        approveRequestsButton.setOnAction(event -> {
            EmployeeApprovedRequests.display();
        });

        addNewsButton.setOnAction(event -> {
            AddNews.display();
        });

        answerQuery.setOnAction(event -> {
            AnswerQuery.display();
        });

        viewFeedbackButton.setOnAction(event -> {
            displayFeedback(FEEDBACK_FILE_PATH, "Feedback");
        });

        viewReviewButton.setOnAction(event -> {
            displayFeedback(REVIEW_FILE_PATH, "Review");
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(approveRequestsButton, addNewsButton, answerQuery, viewFeedbackButton, viewReviewButton);

        Scene scene = new Scene(vbox, 300, 250);
        stage.setScene(scene);
        stage.show();
    }

    public static void displayFeedback(String filePath, String title) {
        StringBuilder feedbackStrings = new StringBuilder();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    feedbackStrings.append(lineNumber).append(". ").append(line).append("\n");
                    lineNumber++;
                }
            }

            UtilClass.showAlert(Alert.AlertType.INFORMATION, title, feedbackStrings.toString());
        } catch (IOException e) {
            UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Failed to read " + title.toLowerCase() + ".");
            e.printStackTrace();
        }
    }

    public static void displayReview(String filePath, String title) {
        StringBuilder reviewStrings = new StringBuilder();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int lineNumber = 1;
            while ((line = br.readLine()) != null) {
                if (!line.isEmpty()) {
                    reviewStrings.append(lineNumber).append(". ").append(line).append("\n");
                    lineNumber++;
                }
            }

            UtilClass.showAlert(Alert.AlertType.INFORMATION, title, reviewStrings.toString());
        } catch (IOException e) {
            UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Failed to read " + title.toLowerCase() + ".");
            e.printStackTrace();
        }
    }
}
