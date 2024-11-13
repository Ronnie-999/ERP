package de.buw.se;

import java.util.Optional;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PostAnswerForQuery {

    public static void display() {
        String answer = customDialog();
        AnswerQuery.setAnswerForQuestion(answer);
    }

    public static String customDialog() {
    
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Query Answer");
        dialog.setHeaderText("Enter your answer:");

        ButtonType submitButtonType = new ButtonType("Submit");
        dialog.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);

        TextField answerField = new TextField();
        answerField.setPromptText("Answer");

        VBox vbox = new VBox(answerField);
        dialog.getDialogPane().setContent(vbox);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                return answerField.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();
        return result.get();
    }
   
}
