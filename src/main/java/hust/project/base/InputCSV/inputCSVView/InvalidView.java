package hust.project.base.InputCSV.inputCSVView;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class InvalidView {
    public static void displayErrorMessage(String header, String message) {
        // Since we don't know the context, ensure this runs on the JavaFX application thread
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(header);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
