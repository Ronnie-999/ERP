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

public class AddMemberFeedback {
    private static final String FEEDBACK_CSV_PATH = "src\\main\\resources\\feedback.csv";
    private static final String EMPLOYEE_CSV_PATH = "src\\main\\resources\\employees.csv";

    public static void display(String currentLoggedInUser) {
        Stage stage = new Stage();
        stage.setTitle("Feedback");

        VBox vbox = new VBox(10);
        Label employeeLabel = new Label("Select an employee:");
        ComboBox<String> employeeComboBox = new ComboBox<>();
        employeeComboBox.setItems(getEmployees());

        Label commentLabel = new Label("Enter your feedback:");
        TextArea commentBox = new TextArea();
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(event -> {
            String selectedEmployee = employeeComboBox.getValue();
            String comment = commentBox.getText().trim();
            if (selectedEmployee == null || selectedEmployee.isEmpty()) {
                UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Please select an employee.");
            } else if (comment.isEmpty()) {
                UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Feedback cannot be empty.");
            } else {
                saveFeedback(selectedEmployee, comment, stage, currentLoggedInUser);
            }
        });

        vbox.getChildren().addAll(employeeLabel, employeeComboBox, commentLabel, commentBox, submitButton);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(200);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private static void saveFeedback(String employee, String comment, Stage stage, String currentLoggedInUser) {
        try {
            writeToCSV(employee, comment, currentLoggedInUser, FEEDBACK_CSV_PATH);
            showAlertAndCloseStage(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static void writeToCSV(String employee, String comment, String currentLoggedInUser, String csvPath) throws IOException {
        try (FileWriter writer = new FileWriter(csvPath, true)) {
            writer.append(employee).append(",").append(comment).append(",").append(currentLoggedInUser).append("\n");
        }
    }

    private static void showAlertAndCloseStage(Stage stage) {
        UtilClass.showAlert(Alert.AlertType.INFORMATION, "Submitted", "Your feedback has been submitted.");
        stage.close();
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

