package de.buw.se;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentTest {

    @Test
    public void testRegisterCompanyOnPaymentSuccess() {
        Company company = new Company("user1", "password1", "Test Company", "1234567890");

        // Simulate a successful payment
        assertTrue(Payment.isPaymentSuccess("x y", 50.0));

        // Perform the company registration
        boolean registrationResult = Payment.registerCompany(company);

        // Assert that the company registration is successful
        assertTrue(registrationResult, "Expected company registration to be successful");
    }

    @Test
    public void testRegisterCompanyOnPaymentFailure() {
        Company company = new Company("user2", "password2", "Another Test Company", "0987654321");

        // Simulate a failed payment
        assertFalse(Payment.isPaymentSuccess("x y", 0.0));

        // Perform the company registration
        boolean registrationResult = Payment.registerCompany(company);

        // Assert that the company registration is unsuccessful
        assertFalse(registrationResult, "Expected company registration to be unsuccessful due to payment failure");
    }

    @Test
    public void testRegisterCompanyWithMissingCompanyInformation() {
        Company company = new Company("user5", "", "", "3344556677");

        // Simulate a successful payment
        assertTrue(Payment.isPaymentSuccess("x y", 50.0));

        // Perform the company registration
        boolean registrationResult = Payment.registerCompany(company);

        // Assert that the company registration is unsuccessful due to missing
        // information
        assertFalse(registrationResult,
                "Expected company registration to be unsuccessful due to missing company information");
    }

}
