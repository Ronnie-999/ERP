package de.buw.se;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AddNewsTest {

    @Test
    public void testAddNews_Success() {
        String news = "Test news";

        // Call addNews method
        boolean result = AddNews.addNews(news);

        assertTrue(result, "Expected news to be added successfully");

        // Verify the news is in the file
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\resources\\news.csv"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.equals(news)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Expected news not found in file");
        } catch (IOException e) {
            fail("IOException occurred during file reading");
        }
    }

    @Test
    public void testAddNews_Empty() {

        String news = "";

        // Call addNews method
        boolean result = AddNews.addNews(news);

        assertFalse(result, "Expected to fail to add empty news");
    }

}
