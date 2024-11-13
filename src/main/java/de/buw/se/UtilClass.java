package de.buw.se;

import javafx.scene.control.Alert;

public class UtilClass {

    public static Alert lastAlert;

    public static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();

        // Store the last shown alert
        lastAlert = alert;
    }

    public static Alert getLastAlert() {
        return lastAlert;
    }

    public static void clearLastAlert() {
        lastAlert = null;
    }
}
