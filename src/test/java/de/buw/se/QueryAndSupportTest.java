package de.buw.se;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryAndSupportTest {

    @Test
    public void testIsValidQuery_valid() {
        assertTrue(QueryAndSupport.isValidQuery("This is a Valid Query"));
    }

    @Test
    public void testIsValidQuery_invalid() {
        assertTrue(QueryAndSupport.isValidQuery("Invalid query, contains comma"));
    }

    @Test
    public void testIsValidQuery_empty() {
        assertFalse(QueryAndSupport.isValidQuery(""));
    }
}
