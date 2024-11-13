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

public class RegisterEmployeeGUITest {

    private static final String TEST_EMPLOYEES_CSV_PATH = "src\\test\\resources\\employees_test.csv";

    @BeforeEach
    public void setUp() throws IOException {
        createTestCsvFile();
    }

    private void createTestCsvFile() throws IOException {
        // Create a test CSV file with headers
        Path path = Paths.get(TEST_EMPLOYEES_CSV_PATH);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("username,password,employeeId,phoneNumber\n");
        }
    }

    @Test
    public void saveEmployeeCredentialsSuccessfully() {
        // Given
        String username = "testuser";
        String password = "testpass";
        String employeeId = "EMP001";
        String phoneNumber = "1234567890";

        // When
        RegisterEmployeeGUI.saveEmployeeCredentials(username, password, employeeId, phoneNumber,
                TEST_EMPLOYEES_CSV_PATH);

        // Then
        // Check if the data was written to the test CSV file correctly
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_EMPLOYEES_CSV_PATH))) {
            // Skip the header line
            reader.readLine();

            // Read the first line of data
            String line = reader.readLine();
            assertNotNull(line, "Expected line of data should not be null");
            assertTrue(line.contains(username), "Username should be present in the CSV");
            assertTrue(line.contains(password), "Password should be present in the CSV");
            assertTrue(line.contains(employeeId), "Employee ID should be present in the CSV");
            assertTrue(line.contains(phoneNumber), "Phone number should be present in the CSV");
        } catch (IOException e) {
            fail("Exception occurred during file read: " + e.getMessage());
        }
    }

    @Test
    public void saveEmployeeCredentialsWithMissingFields() {
        // Given
        String username = "testuser";
        String password = "";
        String employeeId = "EMP002";
        String phoneNumber = "0987654321";

        // When / Then
        IllegalArgumentException thrown = assertThrows(
                IllegalArgumentException.class,
                () -> RegisterEmployeeGUI.saveEmployeeCredentials(username, password, employeeId, phoneNumber,
                        TEST_EMPLOYEES_CSV_PATH),
                "Expected saveEmployeeCredentials to throw, but it didn't");

        assertTrue(thrown.getMessage().contains("All fields are required and must not be empty."));
    }
}
