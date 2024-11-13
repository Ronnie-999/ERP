package de.buw.se;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MemberLoginTest {

    private final MemberLogin memberLogin = new MemberLogin();

    // Test valid credentials
    @Test
    public void testValidateMemberWithValidCredentials() {
        assertTrue(memberLogin.validateMember("000", "000"));
    }

    // Test invalid credentials
    @Test
    public void testValidateMemberWithInvalidCredentials() {
        assertFalse(memberLogin.validateMember("wrongUser", "wrongPass"));
    }

    // Test empty credentials
    @Test
    public void testValidateMemberWithEmptyCredentials() {
        assertAll("Empty credentials should be invalid",
                () -> assertFalse(memberLogin.validateMember("", "")),
                () -> assertFalse(memberLogin.validateMember(null, null)));
    }
}