package hust.project.base.InputCSV.inputCSVView;

import javafx.application.Platform;
import javafx.scene.control.Alert;

public class SuccessView {
    public static void displaySuccessMessage(String header, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(header);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
}
