package hust.project.base.dashboard.View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class Dashboard extends BorderPane {
    private static Dashboard ins;

    private Dashboard() {
        Label label = new Label("DASHBOARD SCREEN");
        label.setStyle("-fx-font-size : 70px");

        Button requestButton = new Button("Yêu cầu chỉnh sửa chấm công");
        requestButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("Yêu cầu chỉnh sửa chấm công button clicked");
            }
        });

        VBox centerContent = new VBox(20);
        centerContent.getChildren().addAll(label, requestButton);
        setCenter(centerContent);
    }

    public static Dashboard instance() {
        if (ins == null) {
            ins = new Dashboard();
        }
        return ins;
    }
}
