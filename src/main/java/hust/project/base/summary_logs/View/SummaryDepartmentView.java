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
    }

    private void loadSummaryData() {
        IHRService iHRService = new HRService();
        List<Employee> allEmployees = iHRService.getAllEmployee();

        for (Employee employee : allEmployees) {
            String employeeId = employee.getEmployeeId();

            for (String period : getAllPeriods()) {
                int totalSessions = summaryController.calculateTotalSessions(employeeId, period);
                double totalLateHours = summaryController.calculateTotalLateHours(employeeId, period);
                double totalEarlyDepartures = summaryController.calculateTotalEarlyDepartures(employeeId, period);

                String departmentName = iHRService.getDepartment(employee.getDepartmentId()).getDepartmentName();
                Summary summary = new Summary(employee.getName(), employeeId, departmentName, period, totalSessions, totalLateHours, totalEarlyDepartures);
                summaryMap.computeIfAbsent(employeeId, k -> new HashMap<>()).put(period, summary);
            }
        }
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
    private void updateTableForYear(String year) {
        List<Summary> summariesForYear = new ArrayList<>();

        for (Map<String, Summary> employeeSummaries : summaryMap.values()) {
            Summary yearlySummary = new Summary ("", "", "", "", 0, 0.0, 0.0);
            int totalSessions = 0;
            double totalLateHours = 0.0;
            double totalEarlyDepartures = 0.0;

            for (String period : employeeSummaries.keySet()) {
                if (period.endsWith(year)) {
                    Summary summary = employeeSummaries.get(period);
                    totalSessions += summary.getTotalSessions();
                    totalLateHours += summary.getTotalLateHours();
                    totalEarlyDepartures += summary.getTotalEarlyDepartures();

                    // Thiết lập thông tin nhân viên và phòng ban cho tổng hợp năm
                    yearlySummary.setEmployeeName(summary.getEmployeeName());
                    yearlySummary.setEmployeeId(summary.getEmployeeId());
                    yearlySummary.setDepartmentName(summary.getDepartmentName());
                }
            }

            yearlySummary.setPeriod("Năm " + year);
            yearlySummary.setTotalSessions(totalSessions);
            yearlySummary.setTotalLateHours(Math.round((totalLateHours) * 100.0) / 100.0);
            yearlySummary.setTotalEarlyDepartures(Math.round((totalEarlyDepartures) * 100.0) / 100.0);


            summariesForYear.add(yearlySummary);
        }

        summaryTable.setItems(FXCollections.observableArrayList(summariesForYear));
    }

    private void updateTableForQuarter(String quarter) {
        List<Summary> summariesForQuarter = new ArrayList<>();

        for (Map<String, Summary> employeeSummaries : summaryMap.values()) {
            Summary quarterlySummary = new Summary ("","","","",0,0,0);
            int totalSessions = 0;
            double totalLateHours = 0.0;
            double totalEarlyDepartures = 0.0;

            for (String period : employeeSummaries.keySet()) {
                if (isPeriodInQuarter(period, quarter)) {
                    Summary summary = employeeSummaries.get(period);
                    totalSessions += summary.getTotalSessions();
                    totalLateHours += summary.getTotalLateHours();
                    totalEarlyDepartures += summary.getTotalEarlyDepartures();

                    // Thiết lập thông tin nhân viên và phòng ban cho tổng hợp quý
                    quarterlySummary.setEmployeeName(summary.getEmployeeName());
                    quarterlySummary.setEmployeeId(summary.getEmployeeId());
                    quarterlySummary.setDepartmentName(summary.getDepartmentName());
                }
            }

            quarterlySummary.setPeriod(quarter);
            quarterlySummary.setTotalSessions(totalSessions);
            quarterlySummary.setTotalLateHours(Math.round((totalLateHours) * 100.0) / 100.0);
            quarterlySummary.setTotalEarlyDepartures(Math.round((totalEarlyDepartures) * 100.0) / 100.0);
            summariesForQuarter.add(quarterlySummary);
        }

        summaryTable.setItems(FXCollections.observableArrayList(summariesForQuarter));
    }

    private boolean isPeriodInQuarter(String period, String quarter) {
        String[] periodParts = period.split("-");
        int periodMonth = Integer.parseInt(periodParts[0]);
        int periodYear = Integer.parseInt(periodParts[1]);

        String[] quarterParts = quarter.split(" - ");
        String quarterYear = quarterParts[1];
        int quarterNumber = Integer.parseInt(quarterParts[0].split(" ")[1]);

        int quarterStartMonth = (quarterNumber - 1) * 3 + 1; // Tháng bắt đầu của quý
        int quarterEndMonth = quarterStartMonth + 2; // Tháng kết thúc của quý

        return periodYear == Integer.parseInt(quarterYear) && periodMonth >= quarterStartMonth && periodMonth <= quarterEndMonth;
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

    private String[] getAllPeriods() {
        AttendanceRecordRepository attendanceRecordRepository = new AttendanceRecordEntity ();
        LocalDate startDate = attendanceRecordRepository.getStartDateFromDB();
        LocalDate endDate = LocalDate.now(); // Hoặc ngày muộn nhất có thể từ DB
        ArrayList<String> periods = new ArrayList<>();

        while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
            String period = startDate.format(DateTimeFormatter.ofPattern("MM-yyyy"));
            periods.add(period);
            startDate = startDate.plusMonths(1);
        }
        return periods.toArray(new String[0]);
    }

    private void initComboBoxes() {
        periodBox = new ComboBox<>(FXCollections.observableArrayList(getAllPeriods()));
        periodBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
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




}
