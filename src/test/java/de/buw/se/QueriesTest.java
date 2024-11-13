package de.buw.se;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueriesTest {

    @Test
    public void testGettersAndSetters() {
        // Create a Queries object
        Queries query = new Queries("What is Java?", "Java is a programming language.", "user1", true);

        // Test getters
        assertEquals("What is Java?", query.getQuestion());
        assertEquals("Java is a programming language.", query.getAnswer());
        assertEquals("user1", query.getUserId());
        assertTrue(query.isAnswered());

        // Test setters
        query.setQuestion("What is Python?");
        query.setAnswer("Python is also a programming language.");
        query.setUserId("user2");
        query.setAnswered(false);

        // Verify using getters
        assertEquals("What is Python?", query.getQuestion());
        assertEquals("Python is also a programming language.", query.getAnswer());
        assertEquals("user2", query.getUserId());
        assertFalse(query.isAnswered());
    }

    @Test
    public void testEmptyStringValidation() {
        // Create a Queries object with initial values
        Queries query = new Queries("Initial question", "Initial answer", "user1", true);

        // Attempt to set empty strings using setters
        try {
            query.setQuestion("");
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        try {
            query.setAnswer("");
            fail("Expected IllegalArgumentException was not thrown");
        } catch (IllegalArgumentException e) {
            // Expected exception; continue
        }

        // Verify that getters return the initial values
        assertEquals("Initial question", query.getQuestion());
        assertEquals("Initial answer", query.getAnswer());
    }

}