package de.buw.se;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Payment {

    private static final double PAYMENT_AMOUNT = 50.0d;

    public enum PAYMENT_OPTIONS {
        BANK_TRANSFER("Bank Transfer"),
        PAYPAL("PayPal"),
        STRIPE("Stripe"),
        DEBIT_CREDIT("Debit or Credit");

        private final String displayName;

        PAYMENT_OPTIONS(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public static class ChoiceItem {
        private final Payment.PAYMENT_OPTIONS option;
        private final String displayName;

        public ChoiceItem(Payment.PAYMENT_OPTIONS option, String displayName) {
            this.option = option;
            this.displayName = displayName;
        }

        public Payment.PAYMENT_OPTIONS getOption() {
            return option;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    public static void display(Company company, List<String> availableNames) {

        Stage stage = new Stage();
        stage.setTitle("Payment");

        VBox vbox = new VBox(10);
        Label label = new Label("Choose one Name:");
        Label totalNames = new Label(" names available");
        vbox.getChildren().addAll(label, totalNames);

        Set<String> existingCompanyNames = fetchExistingCompanyNames();

        List<String> filteredNames = new ArrayList<>();
        for (String name : availableNames) {
            if (!existingCompanyNames.contains(name.trim().toLowerCase())) {
                filteredNames.add(name);
            }
        }

        ChoiceBox<String> companyNameCB = new ChoiceBox<>();
        for (String name : filteredNames) {
            companyNameCB.getItems().add(name);
        }

        if (!filteredNames.isEmpty()) {
            companyNameCB.setValue(filteredNames.get(0));
        }

        vbox.getChildren().add(companyNameCB);

        ChoiceBox<String> paymentOptionCB = new ChoiceBox<>();
        for (PAYMENT_OPTIONS option : PAYMENT_OPTIONS.values()) {
            paymentOptionCB.getItems().add(option.getDisplayName());
        }
        paymentOptionCB.setValue(PAYMENT_OPTIONS.BANK_TRANSFER.getDisplayName());

        vbox.getChildren().addAll(paymentOptionCB);

        Label ibanLabel = new Label("IBAN:");
        TextField ibanTF = new TextField();
        Label bicLabel = new Label("BIC:");
        TextField bicTF = new TextField();

        Label paypalEmailLabel = new Label("PayPal Email:");
        TextField paypalEmailTF = new TextField();
        Label paypalPasswordLabel = new Label("PayPal Password:");
        TextField paypalPasswordTF = new TextField();

        Label stripeEmailLabel = new Label("Stripe Email:");
        TextField stripeEmailTF = new TextField();
        Label stripeTokenLabel = new Label("Stripe Token:");
        TextField stripeTokenTF = new TextField();

        Label cardNumberLabel = new Label("Card Number:");
        TextField cardNumberTF = new TextField();
        Label cardExpiryLabel = new Label("Card Expiry:");
        TextField cardExpiryTF = new TextField();
        Label cardCVCLabel = new Label("Card CVC:");
        TextField cardCVCTF = new TextField();

        Label payeeNameLabel = new Label("Payee Name:");
        TextField payeeNameTF = new TextField();
        Label displayMoney = new Label("Paying €" + PAYMENT_AMOUNT);
        Button proceedPaymentButton = new Button("Proceed to Payment");

        proceedPaymentButton.setOnAction(event -> {
            String payeeName = payeeNameTF.getText().trim();
            double amount = Payment.PAYMENT_AMOUNT;

            if (payeeName.isEmpty()) {
                UtilClass.showAlert(Alert.AlertType.ERROR, "ERROR", "Payee name must not be empty.");
                return;
            }

            String paymentOption = paymentOptionCB.getValue();
            boolean isValid = false;

            switch (paymentOption) {
                case "Bank Transfer":
                    String iban = ibanTF.getText().trim();
                    String bic = bicTF.getText().trim();
                    if (iban.isEmpty() || bic.isEmpty()) {
                        UtilClass.showAlert(Alert.AlertType.ERROR, "ERROR", "IBAN and BIC must not be empty.");
                    } else {
                        isValid = true;
                    }
                    break;
                case "PayPal":
                    String paypalEmail = paypalEmailTF.getText().trim();
                    String paypalPassword = paypalPasswordTF.getText().trim();
                    if (paypalEmail.isEmpty() || paypalPassword.isEmpty()) {
                        UtilClass.showAlert(Alert.AlertType.ERROR, "ERROR", "PayPal email and password must not be empty.");
                    } else {
                        isValid = true;
                    }
                    break;
                case "Stripe":
                    String stripeEmail = stripeEmailTF.getText().trim();
                    String stripeToken = stripeTokenTF.getText().trim();
                    if (stripeEmail.isEmpty() || stripeToken.isEmpty()) {
                        UtilClass.showAlert(Alert.AlertType.ERROR, "ERROR", "Stripe email and token must not be empty.");
                    } else {
                        isValid = true;
                    }
                    break;
                case "Debit or Credit":
                    String cardNumber = cardNumberTF.getText().trim();
                    String cardExpiry = cardExpiryTF.getText().trim();
                    String cardCVC = cardCVCTF.getText().trim();
                    if (cardNumber.isEmpty() || cardExpiry.isEmpty() || cardCVC.isEmpty()) {
                        UtilClass.showAlert(Alert.AlertType.ERROR, "ERROR", "Card number, expiry, and CVC must not be empty.");
                    } else {
                        isValid = true;
                    }
                    break;
            }

            if (isValid && isPaymentSuccess(payeeName, amount)) {
                company.setCompanyName(filteredNames.get(companyNameCB.getSelectionModel().getSelectedIndex()));
                if (Payment.registerCompany(company)) {
                    UtilClass.showAlert(Alert.AlertType.INFORMATION, "REGISTERED",
                            "Paid " + PAYMENT_AMOUNT + " Registration successful. Sent for approval.");
                } else {
                    UtilClass.showAlert(Alert.AlertType.ERROR, "NOT REGISTERED",
                            "An error occurred while registering the company.");
                }
            } else if (!isValid) {
                UtilClass.showAlert(Alert.AlertType.ERROR, "PAYMENT ERROR", "An error occurred while paying.");
            }

            stage.close();
        });

        vbox.getChildren().addAll(payeeNameLabel, payeeNameTF, displayMoney, proceedPaymentButton);

        paymentOptionCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            vbox.getChildren().clear();
            vbox.getChildren().addAll(label, totalNames, companyNameCB, paymentOptionCB);

            switch (newValue) {
                case "Bank Transfer":
                    vbox.getChildren().addAll(ibanLabel, ibanTF, bicLabel, bicTF);
                    break;
                case "PayPal":
                    vbox.getChildren().addAll(paypalEmailLabel, paypalEmailTF, paypalPasswordLabel, paypalPasswordTF);
                    break;
                case "Stripe":
                    vbox.getChildren().addAll(stripeEmailLabel, stripeEmailTF, stripeTokenLabel, stripeTokenTF);
                    break;
                case "Debit or Credit":
                    vbox.getChildren().addAll(cardNumberLabel, cardNumberTF, cardExpiryLabel, cardExpiryTF, cardCVCLabel, cardCVCTF);
                    break;
            }

            vbox.getChildren().addAll(payeeNameLabel, payeeNameTF, displayMoney, proceedPaymentButton);
        });

        Scene scene = new Scene(vbox, 600, 600); // Adjusted window size
        stage.setScene(scene);
        stage.show();
    }

    protected static boolean registerCompany(Company company) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\main\\resources\\pending.csv", true))) {
            System.err.println(company.getCompanyName());
            writer.write(company.getLogin() + "," + company.getPassword() + "," + company.getCompanyName() + ","
                    + company.getPhoneNumber());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isPaymentSuccess(String payeeName, double amount) {
        return true;
    }

    private static Set<String> fetchExistingCompanyNames() {
        Set<String> existingNames = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\member.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    existingNames.add(parts[2].trim().toLowerCase());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return existingNames;
    }
}
