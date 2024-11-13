package de.buw.se;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.List;

public class AnswerQueryTest {

    // Path to the test CSV file
    private static final String TEST_FILE_PATH = "src\\test\\resources\\test_queryandsupport.csv";

    @BeforeAll
    public static void setup() {
        AnswerQuery.setFilePath(TEST_FILE_PATH);
    }

    @BeforeEach
    public void resetTestData() throws IOException {
        String[] testData = {
                "Question1,,User1,false",
                "Question2,,User2,false",
                "Question3,Answer3,User3,true"
        };
        writeTestDataToFile(testData);
    }

    private static void writeTestDataToFile(String[] data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(TEST_FILE_PATH))) {
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    // Tests the fetchQueries method to ensure it reads the CSV file correctly.

    @Test
    public void testFetchQueries() throws IOException {
        List<Queries> queries = AnswerQuery.fetchQueries();
        assertEquals(3, queries.size());

        // Validate the first query
        Queries q1 = queries.get(0);
        assertEquals("Question1", q1.getQuestion());
        assertEquals("", q1.getAnswer());
        assertEquals("User1", q1.getUserId());
        assertFalse(q1.isAnswered());

        // Validate the second query
        Queries q2 = queries.get(1);
        assertEquals("Question2", q2.getQuestion());
        assertEquals("", q2.getAnswer());
        assertEquals("User2", q2.getUserId());
        assertFalse(q2.isAnswered());

        // Validate the third query
        Queries q3 = queries.get(2);
        assertEquals("Question3", q3.getQuestion());
        assertEquals("Answer3", q3.getAnswer());
        assertEquals("User3", q3.getUserId());
        assertTrue(q3.isAnswered());
    }

    // Tests the replaceLineInCSV method to ensure it updates the CSV file

    @Test
    public void testReplaceLineInCSV() throws IOException {
        // Update the second query (index 1) with a new answer
        AnswerQuery.replaceLineInCSV(1, "New Answer2");

        List<Queries> queries = AnswerQuery.fetchQueries();
        assertEquals(3, queries.size());

        // Verify that the second query has been updated
        Queries q2 = queries.get(1);
        assertEquals("Question2", q2.getQuestion());
        assertEquals("New Answer2", q2.getAnswer());
        assertEquals("User2", q2.getUserId());
        assertTrue(q2.isAnswered());

        // Verify that the first query remains unchanged
        Queries q1 = queries.get(0);
        assertEquals("Question1", q1.getQuestion());
        assertEquals("", q1.getAnswer());
        assertEquals("User1", q1.getUserId());
        assertFalse(q1.isAnswered());

        // Verify that the third query remains unchanged
        Queries q3 = queries.get(2);
        assertEquals("Question3", q3.getQuestion());
        assertEquals("Answer3", q3.getAnswer());
        assertEquals("User3", q3.getUserId());
        assertTrue(q3.isAnswered());
    }

    // Tests the setAnswerForQuestion method to ensure it updates the selected query

    @Test
    public void testSetAnswerForQuestion() throws IOException {
        // Set the index to the first question (index 0)
        AnswerQuery.selectedIndex = 0;
        AnswerQuery.setAnswerForQuestion("New Answer1");

        List<Queries> queries = AnswerQuery.fetchQueries();
        assertEquals(3, queries.size());

        // Verify that the first query has been updated
        Queries q1 = queries.get(0);
        assertEquals("Question1", q1.getQuestion());
        assertEquals("New Answer1", q1.getAnswer());
        assertEquals("User1", q1.getUserId());
        assertTrue(q1.isAnswered());

        // Verify that the second query remains unchanged
        Queries q2 = queries.get(1);
        assertEquals("Question2", q2.getQuestion());
        assertEquals("", q2.getAnswer());
        assertEquals("User2", q2.getUserId());
        assertFalse(q2.isAnswered());

        // Verify that the third query remains unchanged
        Queries q3 = queries.get(2);
        assertEquals("Question3", q3.getQuestion());
        assertEquals("Answer3", q3.getAnswer());
        assertEquals("User3", q3.getUserId());
        assertTrue(q3.isAnswered());
    }

    @Test
    public void testSetEmptyAnswerForQuestion() throws IOException {
        // Set the index to the second question (index 1)
        AnswerQuery.selectedIndex = 1;
        AnswerQuery.setAnswerForQuestion("");

        List<Queries> queries = AnswerQuery.fetchQueries();
        assertEquals(3, queries.size());

        // Verify if the second query has been updated with an empty answer
        Queries q2 = queries.get(1);
        assertEquals("Question2", q2.getQuestion());
        assertNotEquals("", q2.getAnswer());
        assertEquals("User2", q2.getUserId());
        assertTrue(q2.isAnswered());

        // Verify that the first query remains unchanged
        Queries q1 = queries.get(0);
        assertEquals("Question1", q1.getQuestion());
        assertEquals("", q1.getAnswer());
        assertEquals("User1", q1.getUserId());
        assertFalse(q1.isAnswered());

        // Verify that the third query remains unchanged
        Queries q3 = queries.get(2);
        assertEquals("Question3", q3.getQuestion());
        assertEquals("Answer3", q3.getAnswer());
        assertEquals("User3", q3.getUserId());
        assertTrue(q3.isAnswered());
    }
}
