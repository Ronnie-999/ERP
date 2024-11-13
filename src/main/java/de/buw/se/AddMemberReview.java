package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddMemberReview {
    private static final String REVIEW_CSV_PATH = "src\\main\\resources\\review.csv";
    private static final String EMPLOYEE_CSV_PATH = "src\\main\\resources\\employees.csv";

    public static void display(String currentLoggedInUser) {
        Stage stage = new Stage();
        stage.setTitle("Review");

        VBox vbox = new VBox(10);
        Label employeeLabel = new Label("Select an employee:");
        ComboBox<String> employeeComboBox = new ComboBox<>();
        employeeComboBox.setItems(getEmployees());

        Label commentLabel = new Label("Enter your review:");
        TextArea commentBox = new TextArea();
        Button submitButton = new Button("Submit");

        Label submittedReviewsLabel = new Label("Submitted Reviews:");
        TextArea reviewsArea = new TextArea();
        reviewsArea.setEditable(false);
        displayReviews(reviewsArea);

        submitButton.setOnAction(event -> {
            String selectedEmployee = employeeComboBox.getValue();
            String comment = commentBox.getText().trim();
            if (selectedEmployee == null || selectedEmployee.isEmpty()) {
                UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Please select an employee.");
            } else if (comment.isEmpty()) {
                UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Review cannot be empty.");
            } else {
                saveReview(selectedEmployee, comment, reviewsArea, currentLoggedInUser);
            }
        });

        vbox.getChildren().addAll(employeeLabel, employeeComboBox, commentLabel, commentBox, submitButton, submittedReviewsLabel, reviewsArea);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(400);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private static void saveReview(String employee, String comment, TextArea reviewsArea, String currentLoggedInUser) {
        try (FileWriter writer = new FileWriter(REVIEW_CSV_PATH, true)) {
            writer.append(employee).append(",").append(comment).append(",").append(currentLoggedInUser).append("\n");
            UtilClass.showAlert(Alert.AlertType.INFORMATION, "Submitted", "Your review has been submitted.");
            displayReviews(reviewsArea); // Update the reviewsArea after submitting a review
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayReviews(TextArea reviewsArea) {
        StringBuilder reviews = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(REVIEW_CSV_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String review = parts[0] + " - '" + parts[1] + "' (submitted by " + parts[2] + ")";
                    reviews.append(review).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reviewsArea.setText(reviews.toString());
    }

    private static ObservableList<String> getEmployees() {
        List<String> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_CSV_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                employees.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(employees);
    }
}
