package hust.project.base.modified.View;
import hust.project.base.modified.Model.ModifiedDAO;
import hust.project.base.modified.Model.ModifiedDTO;
import hust.project.base.modified.Model.ModifiedRepository;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static hust.project.base.constants.MetricsConstants.APPLICATION_HEIGHT;
import static hust.project.base.constants.MetricsConstants.APPLICATION_WIDTH;
public class RejectView {
    public void display(ModifiedDTO modifiedDTO){
        ModifiedRepository modifiedRepository = new ModifiedDAO ();
        Stage stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/image/icon.png"));
        stage.getIcons().add(icon);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Từ chối yêu cầu");
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        Label label = new Label("Từ chối yêu cầu");
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: red;");
        Label label2 = new Label("Bạn chắc chắn từ chối yêu cầu?");
        label2.setAlignment(Pos.CENTER);
        label2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        Button confirmButton = new Button("Chấp nhận");
        confirmButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        Button cancelButton = new Button("Hủy bỏ");
        cancelButton.setStyle("-fx-background-color: #cccccc; -fx-text-fill: white; -fx-font-weight: bold;");
        confirmButton.setOnAction(e -> {
            modifiedRepository.updateRejectModifiedStatus(modifiedDTO.getRequestId());// lambda expression
            System.out.println("Từ chối thành công");
            stage.close();
        });
        cancelButton.setOnAction(e -> {    // lambda expression
            System.out.println("Hủy bỏ");
            stage.close();
        });
        buttonLayout.getChildren().addAll(confirmButton, cancelButton); // Add buttons to the layout
        layout.getChildren().addAll(label,label2, buttonLayout);
        stage.setScene(new Scene(layout, APPLICATION_WIDTH*0.3, APPLICATION_HEIGHT*0.3));
        stage.show();
    }
}
