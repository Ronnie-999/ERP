package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class QueryAndSupport {
    private static final String QUERY_AND_SUPPORT_CSV_PATH = "src\\main\\resources\\queryandsupport.csv";
    private static final String EMPLOYEE_CSV_PATH = "src\\main\\resources\\employees.csv";

    public static void display(String currentLoggedInUser) {
        Stage stage = new Stage();
        stage.setTitle("Query and Support");

        VBox vbox = new VBox(10);
        Label employeeLabel = new Label("Select an employee:");
        ComboBox<String> employeeComboBox = new ComboBox<>();
        employeeComboBox.setItems(getEmployees(currentLoggedInUser));

        Label commentLabel = new Label("Enter your query or support request:");
        TextArea commentBox = new TextArea();
        Button submitButton = new Button("Submit");

        // Add "All Employees" option
        employeeComboBox.getItems().add("All Employees");

        submitButton.setOnAction(event -> {
            String selectedEmployee = employeeComboBox.getValue();
            String comment = commentBox.getText().trim();
            if (selectedEmployee == null || selectedEmployee.isEmpty()) {
                UtilClass.showAlert(Alert.AlertType.ERROR, "Error", "Please select an employee.");
            } else if (isInvalidQuery(comment)) {
                UtilClass.showAlert(Alert.AlertType.WARNING, "Invalid", "Your query cannot be empty, whitespace only, or contain a comma (,).");
            } else {
                if (selectedEmployee.equals("All Employees")) {
                    // Submit for all employees
                    submitForAllEmployees(comment, stage, currentLoggedInUser);
                } else {
                    saveQueryAndSupport(selectedEmployee, comment, stage, currentLoggedInUser);
                }
            }
        });

        vbox.getChildren().addAll(employeeLabel, employeeComboBox, commentLabel, commentBox, submitButton);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(200);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    private static void saveQueryAndSupport(String employee, String comment, Stage stage, String currentLoggedInUser) {
        try (FileWriter writer = new FileWriter(QUERY_AND_SUPPORT_CSV_PATH, true)) {
            writer.append(employee).append(",").append(comment).append(",").append(currentLoggedInUser).append(",").append("false").append("\n");
            UtilClass.showAlert(Alert.AlertType.INFORMATION, "Submitted", "Your query or support request has been submitted.");
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void submitForAllEmployees(String comment, Stage stage, String currentLoggedInUser) {
        try (BufferedReader reader = new BufferedReader(new FileReader(EMPLOYEE_CSV_PATH))) {
            String line;
            FileWriter writer = new FileWriter(QUERY_AND_SUPPORT_CSV_PATH, true);
            while ((line = reader.readLine()) != null) {
                String employee = line.trim();
                if (!employee.isEmpty()) {
                    writer.append(employee).append(",").append(comment).append(",").append(currentLoggedInUser).append(",").append("false").append("\n");
                }
            }
            writer.close();
            UtilClass.showAlert(Alert.AlertType.INFORMATION, "Submitted", "Your query or support request has been submitted for all employees.");
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isInvalidQuery(String query) {
        if (query == null || query.trim().isEmpty()) {
            return true;
        }
        Pattern pattern = Pattern.compile(",");
        Matcher matcher = pattern.matcher(query);
        return matcher.find();
    }

    private static ObservableList<String> getEmployees(String currentLoggedInUser) {
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
