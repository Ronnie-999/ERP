package de.buw.se;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeLoginTest {

    // Test case for valid employee credentials
    @Test
    public void testValidateEmployee_Success() {
        // Mock loginId and password from the CSV file
        String loginId = "111";
        String password = "111";

        // Call the validateEmployee method
        boolean isValid = EmployeeLogin.validateEmployee(loginId, password);

        // Assert that the credentials are valid
        assertTrue(isValid);
    }

    // Test case for invalid employee credentials
    @Test
    public void testValidateEmployee_Failure() {
        // Mock incorrect loginId and password
        String loginId = "unknown_user";
        String password = "wrong_password";

        // Call the validateEmployee method
        boolean isValid = EmployeeLogin.validateEmployee(loginId, password);

        // Assert that the credentials are invalid
        assertFalse(isValid);
    }
}