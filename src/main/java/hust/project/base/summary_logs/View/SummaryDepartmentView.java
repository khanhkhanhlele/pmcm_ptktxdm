package hust.project.base.summary_logs.View;

import hust.project.base.employee_subsystem.Employee;
import hust.project.base.employee_subsystem.HRService;
import hust.project.base.employee_subsystem.IHRService;
import hust.project.base.summary_logs.Controller.SummaryDepartmentController;
import hust.project.base.summary_logs.Controller.SummaryDepartmentController;
import hust.project.base.summary_logs.Model.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static hust.project.base.constants.MetricsConstants.MAIN_WIDTH;

public class SummaryDepartmentView extends VBox {
    private TableView<Summary> summaryTable;
    private ComboBox<String> periodBox;
    private ComboBox<String> yearBox;
    private ComboBox<String> quarterBox;
    private HashMap<String, HashMap<String, Summary>> summaryMap;
    private SummaryDepartmentController summaryController; // Controller


    public SummaryDepartmentView(SummaryDepartmentController controller) {
        this.summaryController = controller;
        setSpacing(20);
        setPrefWidth(MAIN_WIDTH * 0.5);
        setMaxWidth(MAIN_WIDTH * 0.8);

        Label label = new Label("Thông tin chấm công tổng hợp của nhân viên");
        label.setStyle("-fx-font-size: 20px;");

        summaryTable = createSummaryTable();
        summaryMap = new HashMap<>();
        loadSummaryData();
        initComboBoxes();

        HBox selectionBox = createSelectionBox();
        getChildren().addAll(label, selectionBox, summaryTable);
        this.updateTable(this.getCurrentPeriod());
    }



    private void updateTable(String period) {
        List<Summary> summariesForPeriod = new ArrayList<>();
        for (Map<String, Summary> summaries : summaryMap.values()) {
            if (summaries.containsKey(period)) {
                summariesForPeriod.add(summaries.get(period));
            }
        }
        summaryTable.setItems(FXCollections.observableArrayList(summariesForPeriod));
    }


    private TableView<Summary> createSummaryTable() {
        TableView<Summary> table = new TableView<>();
        TableColumn<Summary, Number> sttCol = new TableColumn<>("STT");
        sttCol.setMaxWidth(65);
        sttCol.setCellValueFactory(column -> new ReadOnlyObjectWrapper<> (table.getItems().indexOf(column.getValue()) + 1));
        sttCol.setSortable(false);

        TableColumn<Summary, String> nameColumn = new TableColumn<>("Tên nhân viên");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("employeeName"));
        nameColumn.setMinWidth(100);

        TableColumn<Summary, String> idColumn = new TableColumn<>("ID nhân viên");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        idColumn.setMinWidth(100);

        TableColumn<Summary, String> departmentColumn = new TableColumn<>("Tên Phòng ban");
        departmentColumn.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
        departmentColumn.setMinWidth(150);


        TableColumn<Summary, Integer> sessionsColumn = new TableColumn<>("Tổng công");
        sessionsColumn.setCellValueFactory(new PropertyValueFactory<>("totalSessions"));
        sessionsColumn.setMinWidth(80);

        TableColumn<Summary, Double> lateHoursColumn = new TableColumn<>("Đi muộn");
        lateHoursColumn.setCellValueFactory(new PropertyValueFactory<>("totalLateHours"));
        lateHoursColumn.setMinWidth(80);

        TableColumn<Summary, Double> earlyDeparturesColumn = new TableColumn<>("Về sớm");
        earlyDeparturesColumn.setCellValueFactory(new PropertyValueFactory<>("totalEarlyDepartures"));
        earlyDeparturesColumn.setMinWidth(80);

        TableColumn<Summary, Double> period = new TableColumn<> ("Thời gian");
        period.setCellValueFactory(new PropertyValueFactory<>("period"));


        table.getColumns().addAll(sttCol,nameColumn, idColumn, departmentColumn, sessionsColumn, lateHoursColumn, earlyDeparturesColumn, period);
        table.setMaxWidth(MAIN_WIDTH * 0.8);

        return table;
    }

    private void initComboBoxes() {
        periodBox = new ComboBox<>(FXCollections.observableArrayList(summaryController.getAllPeriods ()));
//        String currentPeriod = getCurrentPeriod();
//        periodBox.setValue(currentPeriod);
//        updateTable(currentPeriod);

        periodBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                System.out.println(newSelection);
                updateTable(newSelection);
                yearBox.setValue(null);
                quarterBox.setValue(null);
            }
        });

        yearBox = new ComboBox<>(FXCollections.observableArrayList( "2022", "2023"));
        yearBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTableForYear(newSelection);
                periodBox.setValue(null);
                quarterBox.setValue(null);
            }
        });

        quarterBox = new ComboBox<>(FXCollections.observableArrayList("Quý 1 - 2023", "Quý 2 - 2023", "Quý 3 - 2023", "Quý 4 - 2023", "Quý 1 - 2022", "Quý 2 - 2022", "Quý 3 - 2022", "Quý 4 - 2022" ));
        quarterBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateTableForQuarter(newSelection);
                periodBox.setValue(null);
                yearBox.setValue(null);
            }
        });


    }

    private HBox createSelectionBox() {
        HBox selectionBox = new HBox(10);
        selectionBox.getChildren().addAll(
                new Label("Chọn Tháng:"), periodBox,
                new Label("Chọn Năm:"), yearBox,
                new Label("Chọn Quý:"), quarterBox
        );
        return selectionBox;
    }

    private void loadSummaryData() {
        summaryMap = summaryController.loadSummaryData(); // Fetch data from controller
    }

    private void updateTableForYear(String year) {
        List<Summary> summariesForYear = summaryController.updateTableForYear(year);
        summaryTable.setItems(FXCollections.observableArrayList(summariesForYear));
    }

    private void updateTableForQuarter(String quarter) {
        List<Summary> summariesForQuarter = summaryController.updateTableForQuarter(quarter);
        summaryTable.setItems(FXCollections.observableArrayList(summariesForQuarter));
    }

    private String getCurrentPeriod() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        return LocalDate.now().format(formatter);
    }

}
