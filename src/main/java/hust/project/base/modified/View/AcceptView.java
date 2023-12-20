package hust.project.base.modified.View;

import hust.project.base.modified.Model.ModifiedDTO;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

import static hust.project.base.constants.MetricsConstants.APPLICATION_HEIGHT;
import static hust.project.base.constants.MetricsConstants.APPLICATION_WIDTH;

public class AcceptView {
    private Button confirmButton, cancelButton;
    private ModifiedDTO currentData;

    private Consumer<ModifiedDTO> onCancelCallback;

    private Consumer<ModifiedDTO> onAcceptCallback;

    // Callbacks
    public void display(ModifiedDTO modifiedDTO) {
        this.currentData = modifiedDTO;

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Chấp nhận yêu cầu");

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        Label label1 = new Label("Chấp nhận yêu cầu");
        label1.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: green;");

        Label label2 = new Label("Bạn chắc chắn CHẤP NHẬN yêu cầu?");
        label2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        setupButtons();
        HBox buttonLayout = new HBox(20, confirmButton, cancelButton);
        buttonLayout.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label1, label2, buttonLayout);

        Scene scene = new Scene(layout, APPLICATION_WIDTH * 0.3, APPLICATION_HEIGHT * 0.3);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void setupButtons() {
        confirmButton = new Button("Chấp nhận");
        confirmButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        confirmButton.setOnAction (this::onAcceptClicked);
        cancelButton = new Button("Hủy bỏ");
        cancelButton.setStyle("-fx-background-color: #cccccc; -fx-text-fill: white; -fx-font-weight: bold;");
        cancelButton.setOnAction(this::onCancelClicked);
    }

    public void setOnConfirmAction(Consumer<ModifiedDTO> onConfirmAction) {
        this.onAcceptCallback= onConfirmAction;
    }

    public void setOnCancelAction(Consumer<ModifiedDTO> onCancelAction) {
        this.onCancelCallback = onCancelAction;
    }

    private void onAcceptClicked(ActionEvent event) {
        if (onAcceptCallback != null && currentData != null) {
            onAcceptCallback.accept(currentData);
        }
    }

    private void onCancelClicked(ActionEvent event) {
        if (onCancelCallback != null && currentData != null) {
            onCancelCallback.accept(currentData);
        }
    }
    public void close() {
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

}
