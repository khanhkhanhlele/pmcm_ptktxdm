
package hust.project.base.modified.View;

import hust.project.base.employee_subsystem.Department;
import hust.project.base.employee_subsystem.Employee;
import hust.project.base.employee_subsystem.HRService;
import hust.project.base.employee_subsystem.IHRService;
import hust.project.base.modified.Model.AttendanceRecordDAO;
import hust.project.base.modified.Model.AttendanceRecordDTO;
import hust.project.base.modified.Model.AttendanceRecordRepository;
import hust.project.base.modified.Model.ModifiedDTO;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.time.LocalDate;
import static hust.project.base.constants.MetricsConstants.APPLICATION_HEIGHT;
import static hust.project.base.constants.MetricsConstants.APPLICATION_WIDTH;

public class ModifiedView {

    private IHRService hrService = new HRService ();
    private TextField nameField;
    private TextField employeeIdField;
    private TextField departmentNameField;
    private TextField approverField;
    private TextField dateField;
    private TextField noteField;
    private TextField requestIdField;
    private TextField originalRecordField;
    private TextField modifiedRecordField;
    private TextField statusField;
    private TextField createdDateField;
    private TextField scannerIdField;

    private TextField requestTypeField;

    private TextField recordIdField;


        public void display(ModifiedDTO data) {

        Stage window = new Stage();
        Image icon = new Image(getClass().getResourceAsStream("/image/icon.png"));
        window.getIcons().add(icon);
        window.setTitle("Chi tiết yêu cầu");
        window.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(25);
        layout.setStyle("-fx-background-color: white; -fx-padding: 30; -fx-border-color: black; -fx-border-radius: 5; -fx-background-radius: 5;");
        layout.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Chi tiết");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        layout.getChildren().add(titleLabel);

        GridPane formLayout = new GridPane();
        formLayout.setVgap(15);
        formLayout.setHgap(25);
        initializeFormFields(data);
        addFormFieldsToGridPane(formLayout);
        formLayout.add(new Label("Họ và tên"), 0, 0);
        formLayout.add(new Label("Mã nhân viên"), 0, 1);
        formLayout.add(new Label("Người duyệt"), 0, 2);
        formLayout.add(new Label("Ngày"), 0, 3);
        formLayout.add(new Label("Ghi chú"), 0, 4);
        formLayout.add(new Label("Id máy quét"), 0, 5);
        formLayout.add(new Label("Id bản ghi"), 0, 6);
        formLayout.add(new Label("Loại yêu cầu"), 2, 0);
        formLayout.add(new Label("Mã yêu cầu"), 2, 1);
        formLayout.add(new Label("Bản ghi gốc"), 2, 2);
        formLayout.add(new Label("Sửa thành"), 2, 3);
        formLayout.add(new Label("Trạng thái"), 2, 4);
        formLayout.add(new Label("Cơ quan"), 2, 5);
        formLayout.add(new Label("Ngày tạo"), 2, 6);
        layout.getChildren().add(formLayout);
        HBox buttonLayout = new HBox(30);
        buttonLayout.setAlignment(Pos.CENTER);
        Button acceptButton = new Button("CHẤP NHẬN");
        acceptButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
        Button rejectButton = new Button("TỪ CHỐI");
        rejectButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        Button cancelButton = new Button("HỦY BỎ");
        cancelButton.setStyle("-fx-background-color: grey; -fx-text-fill: white;");
        buttonLayout.getChildren().addAll(acceptButton, rejectButton, cancelButton);
        layout.getChildren().add(buttonLayout);
        adjustUIBasedOnStatus(data.getRequestStatus(), buttonLayout);
        acceptButton.setOnAction(e -> {
            handleAccept(data);
            window.close();
        });
        rejectButton.setOnAction(e -> {
            handleReject(data);
            window.close();
        });
        cancelButton.setOnAction(e -> window.close());
        Scene scene = new Scene(layout, APPLICATION_WIDTH * 0.6, APPLICATION_HEIGHT * 0.7);
        window.setScene(scene);
        window.showAndWait();
    }

    private void initializeFormFields(ModifiedDTO data){
        AttendanceRecordDTO record;
        AttendanceRecordRepository repository = new AttendanceRecordDAO();
        if (!data.getRecordId().equals ("******")) {
            record = repository.getAttendanceRecordByRecordId(data.getRecordId());
            dateField = new TextField(record.getDate());
            originalRecordField = new TextField(record.getTime ());
            scannerIdField = new TextField(record.getFingerscannerId());
            recordIdField = new TextField(record.getRecordId());
        } else {
            dateField = new TextField(data.getDate());
            originalRecordField = new TextField("");
            scannerIdField = new TextField("");
            recordIdField = new TextField("");
            System.out.println("recordId is null!");
        }
        Employee employee = hrService.getEmployee(data.getEmployeeId());
        Department department = hrService.getDepartment(employee.getDepartmentId());
        nameField = new TextField(employee.getName ()); // Assuming you have a method to get employee name
        employeeIdField = new TextField(data.getEmployeeId());
        departmentNameField = new TextField (department.getDepartmentName ());
        approverField = new TextField("Lê Vũ Minh Tâm");
        noteField = new TextField(data.getRequestReason());
        requestIdField = new TextField(data.getRequestId());
        modifiedRecordField = new TextField(data.getTime());
        statusField = new TextField(data.getRequestStatus());
        createdDateField = new TextField( String.valueOf (LocalDate.now()));
        requestTypeField = new TextField(data.getRequestType());
        disableTextFields();
    }


    private void addFormFieldsToGridPane(GridPane formLayout) {
        formLayout.add(nameField, 1, 0);
        formLayout.add(employeeIdField, 1, 1);
        formLayout.add(approverField, 1, 2);
        formLayout.add(dateField, 1, 3);
        formLayout.add(noteField, 1, 4);
        formLayout.add(scannerIdField, 1, 5);
        formLayout.add(recordIdField, 1, 6);
        formLayout.add(requestTypeField, 3, 0);
        formLayout.add(requestIdField, 3, 1);
        formLayout.add(originalRecordField, 3, 2);
        formLayout.add(modifiedRecordField, 3, 3);
        formLayout.add(statusField, 3, 4);
        formLayout.add(departmentNameField, 3, 5);
        formLayout.add(createdDateField, 3, 6);

    }


    private void disableTextFields() {
        nameField.setEditable(false);
        employeeIdField.setEditable(false);
        departmentNameField.setEditable(false);
        requestTypeField.setEditable(false);
        approverField.setEditable(false);
        dateField.setEditable(false);
        noteField.setEditable(false);
        recordIdField.setEditable(false);
        requestIdField.setEditable(false);
        originalRecordField.setEditable(false);
        modifiedRecordField.setEditable(false);
        statusField.setEditable(false);
        createdDateField.setEditable(false);
        scannerIdField.setEditable(false);
    }
//    ĐỂ BACKGROUND COLOR CỦA TEXTFIELD FALSE ENITABLE LÀ MÀU XÁM
    private void disableTextColor(){
        nameField.setStyle("-fx-background-color: #E8E8E8");
        employeeIdField.setStyle("-fx-background-color: #E8E8E8");
        departmentNameField.setStyle("-fx-background-color: #E8E8E8");
        requestTypeField.setStyle("-fx-background-color: #E8E8E8");
        approverField.setStyle("-fx-background-color: #E8E8E8");
        recordIdField.setStyle("-fx-background-color: #E8E8E8");
        dateField.setStyle("-fx-background-color: #E8E8E8");
        noteField.setStyle("-fx-background-color: #E8E8E8");
        requestIdField.setStyle("-fx-background-color: #E8E8E8");
        originalRecordField.setStyle("-fx-background-color: #E8E8E8");
        modifiedRecordField.setStyle("-fx-background-color: #E8E8E8");
        statusField.setStyle("-fx-background-color: #E8E8E8");
        createdDateField.setStyle("-fx-background-color: #E8E8E8");
        scannerIdField.setStyle("-fx-background-color: #E8E8E8");
    }
     private void adjustUIBasedOnStatus(String status, HBox buttonLayout){
        if ("Accepted".equals(status) || "Rejected".equals(status)) {
            buttonLayout.setVisible(false); // Hide buttons
            disableTextFields();
            disableTextColor();
        }
        if ("Pending".equals(status)) {
            disableTextFields();
            disableTextColor();
        }
    }

    private void handleAccept(ModifiedDTO data) {
        AcceptView acceptView = new AcceptView();
        acceptView.display(data);

    }

    private void handleReject(ModifiedDTO data) {
        RejectView rejectView = new RejectView();
        rejectView.display(data);
    }
}
