package de.buw.se;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnswerQuery {

    private static String filePath = "src\\main\\resources\\queryandsupport.csv";

    protected static int selectedIndex;

    public static void display() {

        Stage stage = new Stage();
        stage.setTitle("Queries");

        VBox vbox = new VBox(10);
        Label label = new Label("Pending Queries:");
        vbox.getChildren().add(label);

        List<Queries> allQueries = fetchQueries();

        int count = 0;
        List<Integer> notAnsweredIndex = new ArrayList<>();
        for (Queries query : allQueries) {
            if (!query.isAnswered()) {
                Label entryLabel = new Label((notAnsweredIndex.size() + 1) + ". Question: " + query.getQuestion());
                vbox.getChildren().add(entryLabel);
                notAnsweredIndex.add(count);
            }
            count++;
        }

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        for (int i = 0; i < notAnsweredIndex.size(); i++) {
            choiceBox.getItems().add(String.valueOf(i + 1));
        }
        choiceBox.setValue("1"); // Set default value

        Button approveButton = new Button("Answer");
        approveButton.setOnAction(event -> {
            selectedIndex = notAnsweredIndex.get(choiceBox.getSelectionModel().getSelectedIndex());
            System.out.println(selectedIndex);
            if (selectedIndex != -1) {

                PostAnswerForQuery.display();

                stage.close();
            } else {
                UtilClass.showAlert(Alert.AlertType.ERROR, "No Selection", "Please select a request to approve.");
            }
        });

        vbox.getChildren().addAll(choiceBox, approveButton);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();

    }

    static List<Queries> fetchQueries() {
        List<Queries> queriesList = new ArrayList<>();
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] queryData = line.split(csvSplitBy);

                if (queryData.length == 4) {
                    String question = queryData[0];
                    String answer = queryData[1];
                    String login = queryData[2];
                    boolean isAnswered = Boolean.parseBoolean(queryData[3]);

                    Queries query = new Queries(question, answer, login, isAnswered);
                    queriesList.add(query);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return queriesList;
    }

    static void setAnswerForQuestion(String answer) {
        replaceLineInCSV(selectedIndex, answer);
    }

    static void replaceLineInCSV(int lineIndex, String answer) {

        List<Queries> queries = fetchQueries();
        Queries query = queries.get(lineIndex);
        query.setAnswer(answer);
        query.setAnswered(true);

        List<String> lines = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String newLine = query.getQuestion() + "," + query.getAnswer() + "," + query.getUserId() + ","
                + query.isAnswered();
        if (lineIndex >= 0 && lineIndex < lines.size()) {
            lines.set(lineIndex, newLine);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setFilePath(String path) {
        filePath = path;
    }

    public static String getFilePath() {
        return filePath;
    }
}
