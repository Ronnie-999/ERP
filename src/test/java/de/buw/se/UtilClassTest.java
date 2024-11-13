package de.buw.se;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UtilClassTest {

    private Alert.AlertType alertType;
    private String title;
    private String content;

    @BeforeEach
    public void setUp() {
        alertType = Alert.AlertType.INFORMATION;
        title = "Test Alert";
        content = "This is a test alert.";

        // Initialize JavaFX
        Platform.startup(() -> {

        });
    }

    @AfterEach
    public void tearDown() {
        UtilClass.clearLastAlert();
        // Shutdown JavaFX after each test
        Platform.exit();
    }

    @Test
    public void testShowAlert() {
        // Run showAlert on JavaFX Application Thread
        Platform.runLater(() -> {
            UtilClass.showAlert(alertType, title, content);

            Alert lastAlert = UtilClass.getLastAlert();
            assertNotNull(lastAlert);
            assertEquals(alertType, lastAlert.getAlertType());
            assertEquals(title, lastAlert.getTitle());
            assertEquals(content, lastAlert.getContentText());
        });
    }
}
