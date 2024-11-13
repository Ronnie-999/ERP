package de.buw.se;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainPageGUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Startup Page");

        Label welcomeLabel = new Label("Welcome to the ERP:");
        Button searchButton = new Button("Search for registered companies");
        Button memberLoginButton = new Button("Member login");
        Button employeeLoginButton = new Button("Employee login");
        Button exitButton = new Button("Exit");

        searchButton.setOnAction(event -> CompanySearch.display());
        memberLoginButton.setOnAction(event -> MemberLogin.display());
        employeeLoginButton.setOnAction(event -> EmployeeLogin.display());
        exitButton.setOnAction(event -> primaryStage.close());

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(welcomeLabel, searchButton, memberLoginButton, employeeLoginButton, exitButton);
        vbox.setPrefWidth(300);
        vbox.setPrefHeight(200);

        Scene scene = new Scene(vbox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
