
package de.buw.se;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class AddMemberFeedbackTest {

    private static final String TEST_CSV_PATH = "src\\test\\resources\\feedbackTest.csv";

    @BeforeEach
    public void setUp() throws IOException {
        createTestCsvFile();
    }

    private void createTestCsvFile() throws IOException {
        // Create a test CSV file with headers
        Path path = Paths.get(TEST_CSV_PATH);
        Files.createDirectories(path.getParent());
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("comment,user\n");
        }
    }

    @Test
    public void writeToCSVSuccessfully() {
        // Given
        String comment = "This is a test feedback.";
        String user = "testuser";

        // When
        try {
            AddMemberFeedback.writeToCSV(comment, user, TEST_CSV_PATH);
        } catch (IOException e) {
            fail("Exception occurred during writing to CSV: " + e.getMessage());
        }

        // Then
        // Check if the data was written to the test CSV file correctly
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_CSV_PATH))) {
            // Skip the header line
            reader.readLine();

            // Read the first line of data
            String line = reader.readLine();
            assertNotNull(line, "Expected line of data should not be null");
            assertTrue(line.contains(comment), "Comment should be present in the CSV");
            assertTrue(line.contains(user), "User should be present in the CSV");
        } catch (IOException e) {
            fail("Exception occurred during file read: " + e.getMessage());
        }
    }

    @Test
    public void writeToCSVWithEmptyComment() {
        // Given
        String comment = "   "; // Empty comment after trimming
        String user = "testuser";

        // When
        try {
            AddMemberFeedback.writeToCSV(comment, user, TEST_CSV_PATH);
        } catch (IOException e) {
            fail("Exception occurred during writing to CSV: " + e.getMessage());
        }

        // Then
        // Check if the data was written to the test CSV file correctly
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_CSV_PATH))) {
            // Skip the header line
            reader.readLine();

            // Try to read the next line (there should be none)
            String line = reader.readLine();
            assertNull(line, "No data should be present in the CSV for empty comment");
        } catch (IOException e) {
            fail("Exception occurred during file read: " + e.getMessage());
        }
    }
}
