package hust.project.base.InputCSV.inputCSVView;

import hust.project.base.InputCSV.inputCSVModel.AttendanceRecord;
import hust.project.base.InputCSV.inputCSVModel.InputAttendanceRecordRepository;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import static hust.project.base.constants.MetricsConstants.MAIN_WIDTH;
public class InputCsvView extends VBox {
    private TableView<AttendanceRecord> AttendanceTableView;
    private Button importCsvButton;

    private Button refreshButton;

    private InputAttendanceRecordRepository repository;
    private static InputCsvView ins;

    public static InputCsvView instance() {
        if(ins == null){
            ins = new InputCsvView(ins.repository);
        }
        return ins;
    }


    public InputCsvView(InputAttendanceRecordRepository repository){
        this.repository = repository;
        setSpacing(20);
        setPrefWidth(MAIN_WIDTH*0.5);
        setMaxWidth(MAIN_WIDTH*0.8);
        Label label = new Label("Danh sách chấm công");
        label.setStyle("-fx-font-size: 20px;");
        AttendanceTableView = createAttendanceTable();
        importCsvButton = new Button("Nhập dữ liệu chấm công");
        importCsvButton.setOnAction(event -> onImportCsv());
        refreshButton = new Button("Làm mới dữ liệu");
        refreshButton.setOnAction(event -> refreshTableData());
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10); // Set spacing between buttons
        buttonBox.getChildren().addAll(importCsvButton, refreshButton);
        getChildren().addAll(label,buttonBox, AttendanceTableView);

    }
    private TableView<AttendanceRecord> createAttendanceTable(){
        TableView<AttendanceRecord> table = new TableView<>();
        table.setEditable(true);
        TableColumn<AttendanceRecord, Number> sttCol = new TableColumn<>("STT");
        sttCol.setMinWidth(50);
        sttCol.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(column.getValue()) + 1));
        sttCol.setSortable(false);
        table.getColumns().addAll(
                sttCol,
                createColumn("Mã chấm công", "recordId", String.class, 200),
                createColumn("Nhân viên", "employeeId", String.class),
                createColumn("Mã máy vân tay", "fingerscannerId", String.class, 200),
                createColumn("Ngày checkin", "date", String.class),
                createColumn("Giờ checkin", "time", String.class)

        );
        table.setPrefHeight(25 * 16);
        table.setMaxWidth (MAIN_WIDTH * 0.80);
        return table;
    }
    private <T> TableColumn<AttendanceRecord, T> createColumn(String title, String property, Class<T> type, double... maxWidth) {
        TableColumn<AttendanceRecord, T> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setMinWidth (130);
        return column;
    }


    public void updateTable(List<AttendanceRecord> data) {
        AttendanceTableView.setItems(FXCollections.observableArrayList(data));
        System.out.println("Updated table data");
    }

    private void refreshTableData() {
        List<AttendanceRecord> records = repository.getAllAttendanceRecord(); // Get latest data
        updateTable(records); // Update table with new data
        System.out.println("Refreshed table data");
    }

    private void onImportCsv() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            importCsvData(selectedFile);
        }

        System.out.println("Imported CSV data done!");
    }

    private void importCsvData(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Assuming CSV format: recordId,employeeId,fingerscannerId,date,time
                String[] fields = line.split(",");
                               AttendanceRecord record = new AttendanceRecord(fields[0],fields[1],fields[2], fields[3], fields[4]);
                repository.insertAttendanceRecord(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}