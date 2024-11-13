package de.buw.se;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompanyTest {

    @Test
    void testGettersAndSetters() {
        // Create a Company object with initial values
        Company company = new Company("testLogin", "testPassword", "Test Company", "123456789");

        // Test the initial values using getter methods
        assertEquals("testLogin", company.getLogin());
        assertEquals("testPassword", company.getPassword());
        assertEquals("Test Company", company.getCompanyName());
        assertEquals("123456789", company.getPhoneNumber());

        // Update the values using setter methods
        company.setLogin("newLogin");
        company.setPassword("newPassword");
        company.setCompanyName("New Company");
        company.setPhoneNumber("987654321");

        // Verify the updated values using getter methods
        assertEquals("newLogin", company.getLogin());
        assertEquals("newPassword", company.getPassword());
        assertEquals("New Company", company.getCompanyName());
        assertEquals("987654321", company.getPhoneNumber());
    }

    @Test
    void testEmptyStringValues() {
        // Create a Company object with empty string values
        Company company = new Company("", "", "", "");

        // Check the values are empty strings
        assertEquals("", company.getLogin());
        assertEquals("", company.getPassword());
        assertEquals("", company.getCompanyName());
        assertEquals("", company.getPhoneNumber());

        // Ensure the test fails because we do not want empty string values
        assertFalse(company.getLogin().isEmpty(), "Login should not be empty");
        assertFalse(company.getPassword().isEmpty(), "Password should not be empty");
        assertFalse(company.getCompanyName().isEmpty(), "Company Name should not be empty");
        assertFalse(company.getPhoneNumber().isEmpty(), "Phone Number should not be empty");
    }
}
