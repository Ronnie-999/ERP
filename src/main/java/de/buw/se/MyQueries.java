package de.buw.se;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert;

public class MyQueries {

    public static final String QUERY_SUPPORT_FILE_PATH = "src\\main\\resources\\queryandsupport.csv";

    public static void display(String currentLoggedInUser) {
        List<Queries> curUserQueries = searchQueriesByUserId(currentLoggedInUser);
        if(curUserQueries.size() > 0) {
            StringBuilder queries = new StringBuilder();
            for (Queries query: curUserQueries) {
                queries.append("Query: ").append(query.getQuestion());
                if(query.isAnswered())
                    queries.append("\nAnswer: ").append(query.getAnswer()).append("\n\n");
                else
                    queries.append("\nNot Answered.\n\n");
            }
            UtilClass.showAlert(Alert.AlertType.INFORMATION, "My Queries", queries.toString());
        }
        else {
            UtilClass.showAlert(Alert.AlertType.INFORMATION, "NOT FOUND", "No queries found for this user.");
        }
    }

    public static List<Queries> searchQueriesByUserId( String userId) {
        List<Queries> userQueries = new ArrayList<>();
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(QUERY_SUPPORT_FILE_PATH))) {
            while ((line = br.readLine()) != null) {
                String[] queryData = line.split(csvSplitBy);

                if (queryData.length == 4 && queryData[2].equals(userId)) {
                    String question = queryData[0];
                    String answer = queryData[1];
                    boolean isAnswer = Boolean.parseBoolean(queryData[3]);

                    Queries query = new Queries(question, answer, userId, isAnswer);
                    userQueries.add(query);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userQueries;
    } 
}
