package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterCompany {

    private static final String MEMBER_CSVFILE_PATH = "src\\main\\resources\\member.csv";

    private static List<String> availableNames;

    public static void display() {
        Stage stage = new Stage();
        stage.setTitle("Register Company");

        Label loginIdLabel = new Label("Login ID:");
        TextField loginIdField = new TextField();
        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        Label proposedCompanyLabel = new Label("Proposed Company Names:");
        Label companyName1Label = new Label("Proposed Name1");
        TextField companyName1TF = new TextField();
        Label companyName2Label = new Label("Proposed Name2");
        TextField companyName2TF = new TextField();
        Label companyName3Label = new Label("Proposed Name3");
        TextField companyName3TF = new TextField();
        Label phoneNumberLabel = new Label("Phone Number:");
        TextField phoneNumberField = new TextField();
        
        Button proceedPaymentButton = new Button("Proceed to Payment");

        proceedPaymentButton.setOnAction(event -> {
            String loginId = loginIdField.getText().trim();
            String password = passwordField.getText().trim();
            String companyName1 = companyName1TF.getText().trim();
            String companyName2 = companyName2TF.getText().trim();
            String companyName3 = companyName3TF.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            
            if (loginId.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() ||
                companyName1.isEmpty() && companyName2.isEmpty() && companyName3.isEmpty()) {
                UtilClass.showAlert(Alert.AlertType.ERROR, "ERROR", "All fields must be filled out.");
                return;
            }

            List<String> companyNames = Arrays.asList(companyName1, companyName2, companyName3);
            Company company = new Company(loginId, password, "", phoneNumber);
            System.out.println(company.getLogin());

            RegisterCompany.availableNames = getAvailbleNames(companyNames);

            if (availableNames.size() > 0) {
                Payment.display(company, companyNames);
                stage.close();
            } else {
                UtilClass.showAlert(Alert.AlertType.NONE, "UNAVAILABLE", "Company names unavailable.");
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(loginIdLabel, loginIdField, passwordLabel, passwordField, proposedCompanyLabel, companyName1Label,
                                    companyName1TF, companyName2Label, companyName2TF, companyName3Label, companyName3TF, phoneNumberLabel, phoneNumberField, proceedPaymentButton);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(250);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }

    public static List<Company> fetchCompanies() {
        List<Company> companies = new ArrayList<>();
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(RegisterCompany.MEMBER_CSVFILE_PATH))) {
            while ((line = br.readLine()) != null) {
                String[] companyData = line.split(csvSplitBy);

                if (companyData.length == 4) {
                    String login = companyData[0];
                    String password = companyData[1];
                    String companyName = companyData[2];
                    String phoneNumber = companyData[3];

                    Company company = new Company(login, password, companyName, phoneNumber);
                    companies.add(company);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return companies;
    }

    public static List<String> getAvailbleNames(List<String> companyNames) {
        return companyNames.stream().filter(RegisterCompany::isCompanyNameUnique).collect(Collectors.toList());
    }

    public static boolean isCompanyNameUnique(String companyName) {
        if (companyName.isEmpty()) {
            return false;
        }
        List<Company> companies = fetchCompanies();
        for (Company company : companies) {
            if (company.getCompanyName().equals(companyName)) {
                return false;
            }
        }
        return true;
    }
}

