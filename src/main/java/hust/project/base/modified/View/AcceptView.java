package hust.project.base.modified.View;
import hust.project.base.modified.Model.*;
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
public class AcceptView {
    AttendanceRecordRepository repository = new AttendanceRecordDAO ();
    ModifiedRepository modifiedRepository = new ModifiedDAO ();
    public void display(ModifiedDTO modifiedDTO) {
        Stage stage = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/image/icon.png"));
        stage.getIcons().add(icon);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Chấp nhận yêu cầu");
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        Label label1 = new Label("Chấp nhận yêu cầu");
        label1.setAlignment(Pos.CENTER);
        label1.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;  -fx-text-fill: green;");
        Label label2 = new Label("Bạn chắc chắn CHẤP NHẬN yêu cầu?");
        label2.setAlignment(Pos.CENTER);
        label2.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        Button confirmButton = new Button("Chấp nhận");
        confirmButton.setStyle("-fx-background-color: green; -fx-text-fill: white; -fx-font-weight: bold;");
        Button cancelButton = new Button("Hủy bỏ");
        cancelButton.setStyle("-fx-background-color: #cccccc; -fx-text-fill: white; -fx-font-weight: bold;");
        confirmButton.setOnAction(e -> {
            if ("Chỉnh sửa chấm công".equals(modifiedDTO.getRequestType())) {
                    repository.updateAttendanceRecord(modifiedDTO.getTime (),modifiedDTO.getRecordId());
                    modifiedRepository.updateAcceptModifiedStatus (modifiedDTO.getRequestId());

            } else if ("Thêm chấm công".equals(modifiedDTO.getRequestType ())) {
                String newRecordId = repository.generateNextRecordId();
                AttendanceRecordDTO attendanceRecordDTO = new AttendanceRecordDTO();
                attendanceRecordDTO.setRecordId(newRecordId);
                attendanceRecordDTO.setFingerscannerId("0");
                attendanceRecordDTO.setDate(modifiedDTO.getDate ());
                attendanceRecordDTO.setEmployeeId (modifiedDTO.getEmployeeId ());
                attendanceRecordDTO.setTime (modifiedDTO.getTime ());// Set scanId to null
                repository.insertAttendanceRecord(attendanceRecordDTO);
                modifiedRepository.updateAcceptModifiedRecordId(modifiedDTO.getRequestId(), newRecordId);
            }

            System.out.println("Chấp nhận yêu cầu thành công");
            stage.close();
        });
        cancelButton.setOnAction(e -> {    // lambda expression
            System.out.println("Hủy bỏ");
            stage.close();
        });
        buttonLayout.getChildren().addAll(confirmButton, cancelButton); // Add buttons to the layout
        layout.getChildren().addAll(label1,label2, buttonLayout);
        stage.setScene(new Scene(layout, APPLICATION_WIDTH*0.3, APPLICATION_HEIGHT*0.3));
        stage.show();
    }


}
