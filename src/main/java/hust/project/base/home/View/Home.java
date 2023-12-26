package hust.project.base.home;

import hust.project.base.header.SearchCallBack;
import hust.project.base.home.View.EditView;
import hust.project.base.navbar.NavBarController;
import hust.project.base.header.Header;
import hust.project.base.navbar.Navbar;
import hust.project.base.summary_logs.Model.AttendanceRecordEntity;
import hust.project.base.summary_logs.Model.AttendanceRecordRecord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static hust.project.base.constants.MetricsConstants.BG_COLOR_MAIN;
import static hust.project.base.constants.MetricsConstants.LAYOUT_X_MAIN;
import static hust.project.base.constants.MetricsConstants.MAIN_WIDTH;

public class Home extends HBox{
    private SearchCallBack searchCallBack;
    private Pane currentScreen;
    VBox vboxMain;
    private static Home ins;
    private Label employeeIdLabel, recordID, fingerScanner;

    private TableView<AttendanceRecordRecord> attendanceTable;
    private TableColumn<AttendanceRecordRecord, String> recordIdColumn;
    private TableColumn<AttendanceRecordRecord, String> employeeIdColumn;
    private TableColumn<AttendanceRecordRecord, String> fingerscannerIdColumn;
    private TableColumn<AttendanceRecordRecord, String> dateColumn;
    private TableColumn<AttendanceRecordRecord, String> timeColumn;
    private TableColumn<AttendanceRecordRecord,Void> requestChangeInfor;
    private TableColumn<AttendanceRecordRecord,Void> deleteColumn;

    public static Home instance() {
        if (ins == null) {
            ins = new Home();
        }
        return ins;
    }

    private Home() {
        setStyle("-fx-background-color : " + BG_COLOR_MAIN);
        NavBarController.instance().init();
        Navbar navbar = Navbar.instance();
        getChildren().add(navbar);
        getChildren().add(Main());
    }

    private Pane Main() {
        Pane pane = new Pane();
        pane.setStyle("-fx-padding: 10px");
        vboxMain = new VBox();
        vboxMain.setSpacing(5);
        vboxMain.setStyle("-fx-padding: 10px;");
        vboxMain.setLayoutX(LAYOUT_X_MAIN);
        Header header = new Header();
        Line line = new Line(0, 0, MAIN_WIDTH * 0.7, 0);
        currentScreen = HomeScreen();
        vboxMain.getChildren().addAll(header, line, currentScreen);
        pane.getChildren().add(vboxMain);

        return pane;
    }

    public void setCurrentScreen(Pane body) {
        System.out.println("current screen ..." + (currentScreen.getClass().getName()));
        System.out.println("changing screen ..." + (body.getClass().getName()));
        if (currentScreen.equals(body)) {
            return;
        }
        this.currentScreen.setVisible(false);
        vboxMain.getChildren().remove(currentScreen);
        vboxMain.getChildren().add(body);

        this.currentScreen = body;
        this.currentScreen.setVisible(true);
    }

    public void setCurrentScreen() {
        setCurrentScreen(HomeScreen());
    }

    private BorderPane HomeScreen() {
        BorderPane home = new BorderPane();
        attendanceTable = new TableView<>();
        // Tạo TableView và cột
        recordIdColumn = new TableColumn<>("Record ID");
        employeeIdColumn = new TableColumn<>("Employee ID");
        fingerscannerIdColumn = new TableColumn<>("Fingerscanner ID");
        dateColumn = new TableColumn<>("Date");
        timeColumn = new TableColumn<>("Time");
        requestChangeInfor = new TableColumn<>("Request");
        deleteColumn = new TableColumn<>("Xóa");

        // Đặt giá trị của cột để liên kết với trường tương ứng trong AttendanceRecordRecord
        recordIdColumn.setCellValueFactory(new PropertyValueFactory<>("recordId"));
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        fingerscannerIdColumn.setCellValueFactory(new PropertyValueFactory<>("fingerscannerId"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        requestChangeInfor.setCellFactory(column -> new TableCell<>() {
            private final Button requestButton = new Button("Request");
            {
                // Set up the button action
                requestButton.setOnAction(event -> {
                    AttendanceRecordRecord attendanceRecordRecord = getTableView().getItems().get(getIndex());
                    handleRequestButtonClicked(attendanceRecordRecord);
                    attendanceTable.refresh();
                    // You may choose to refresh the table or perform other actions here
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                // Ensure the cell is empty and not in the header
                if (empty) {
                    setGraphic(null);
                } else {
                    // Set the button as the graphic for the cell
                    setGraphic(requestButton);
                }
            }
        });

        deleteColumn.setCellFactory(column -> new TableCell<>() {
            private final Button deleteButton = new Button("Xóa");
            {
                // Set up the button action
                deleteButton.setOnAction(event -> {
                    AttendanceRecordRecord attendanceRecordRecord = getTableView().getItems().get(getIndex());
                    handleDeleteButtonClicked(attendanceRecordRecord);
                    attendanceTable.refresh();
                    // You may choose to refresh the table or perform other actions here
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                // Ensure the cell is empty and not in the header
                if (empty) {
                    setGraphic(null);
                } else {
                    // Set the button as the graphic for the cell
                    setGraphic(deleteButton);
                }
            }
        });
        // Thêm cột vào TableView

        attendanceTable.getColumns().addAll(recordIdColumn, employeeIdColumn, fingerscannerIdColumn, dateColumn, timeColumn,requestChangeInfor, deleteColumn);

        home.setBottom(attendanceTable);

        updateAttendanceTable();

        return home;
    }

    private void updateAttendanceTable() {

        //Stringifid  =..
        List<AttendanceRecordRecord> attendanceItems = new AttendanceRecordEntity().getAttendanceRecordByEmployeeId("EMP001");
        ObservableList<AttendanceRecordRecord> observableAttendanceItems = FXCollections.observableArrayList(attendanceItems);
        attendanceTable.setItems( observableAttendanceItems);
    }
    private void handleRequestButtonClicked(AttendanceRecordRecord attendanceRecordRecord){
        //load edit form
        String maNV = attendanceRecordRecord.getEmployeeId();
        String machineID = attendanceRecordRecord.getFingerscannerId();
        String idRecord = attendanceRecordRecord.getRecordId();
        DatePicker datePicker = convertStringtoDate(attendanceRecordRecord.getDate());
        String time = attendanceRecordRecord.getTime();
        Pane editPane = new EditView().getPaneRequestEdit( maNV, machineID, idRecord, datePicker, time);
        Stage editStage = new Stage();
        editStage.setTitle("Edit Attendance Record");
        Scene scene = new Scene(editPane);
        editStage.setScene(scene);
        editStage.showAndWait();

    }

    private void handleDeleteButtonClicked(AttendanceRecordRecord attendanceRecordRecord){
        //load edit form
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Bạn muốn xóa log chấm công này");
        alert.showAndWait();
        //if oki thi gui yeu cau xoa
    }
    public DatePicker convertStringtoDate(String date){
        //"11-29-2023"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Parse the string to LocalDate
        LocalDate localDate = LocalDate.parse(date, formatter);

        // Create a DatePicker and set its value
        DatePicker datePicker = new DatePicker(localDate);

        return datePicker;
    }
}
