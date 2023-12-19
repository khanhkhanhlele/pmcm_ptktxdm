package hust.project.base.summary_logs;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

import javafx.scene.layout.Priority;
import static hust.project.base.constants.MetricsConstants.MAIN_WIDTH;


public class SummaryDepartmentView extends VBox {
    List<OfficerSummaryRecord> officerSummaryRecords;
    List<WorkerSummaryRecord> workerSummaryRecords;

    private static SummaryDepartmentView ins;
    public static SummaryDepartmentView instance(){
        if(ins == null){
            ins = new SummaryDepartmentView();
        }
        return ins;
    }
    public SummaryDepartmentView(){
        setSpacing(20);
        setPrefWidth(MAIN_WIDTH*0.5);
        setMaxWidth(MAIN_WIDTH*0.8);
        Label label = new Label("Thông tin chấm công tổng hợp của phòng ban");
        label.setStyle("-fx-font-size: 20px;");
        getChildren().addAll(label, TableHeader(),SummaryTable());

    }
    ComboBox<String> departmentBox = new ComboBox<>();
    ComboBox<String> chooseYearBox = new ComboBox<>();
    ComboBox<String> chooseQuarterBox = new ComboBox<>();
    ComboBox<String> chooseMonthBox = new ComboBox<>();

    private HBox TableHeader(){
        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: #ffffff");
        hbox.setPrefHeight(50);
        hbox.setSpacing(50);
        hbox.setPrefWidth(MAIN_WIDTH * 0.7);

        HBox chooseDepartment = new HBox();
        chooseDepartment.setSpacing(20);
        Label departmentLabel = new Label("Đơn vị");
        chooseDepartment.getChildren().addAll(departmentLabel, departmentBox);

        HBox chooseYear = new HBox();
        chooseYear.setSpacing(20);
        Label yearLabel = new Label("Năm");
        chooseYearBox.setItems(FXCollections.observableArrayList("2023","2022","2021","2020"));
        chooseYear.getChildren().addAll(yearLabel, chooseYearBox);

        HBox chooseQuarter = new HBox();
        chooseQuarter.setSpacing(20);
        Label quarterLabel = new Label("Quý");
        chooseQuarterBox.setItems(FXCollections.observableArrayList("1","2","3","4"));
        chooseQuarter.getChildren().addAll(quarterLabel, chooseQuarterBox);

        HBox chooseMonth = new HBox();
        chooseMonth.setSpacing(20);
        Label monthLabel = new Label("Tháng");
        chooseMonthBox.setItems(FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12"));
        chooseMonth.getChildren().addAll(monthLabel, chooseMonthBox);

        hbox.getChildren().addAll(chooseDepartment, chooseYear,chooseQuarter, chooseMonth);
        return hbox;
    }
    TableView tableView;

    private TableView SummaryTable(){
        tableView = new TableView();

        TableColumn codeCol = new TableColumn("Mã nhân viên");
        codeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        double columnWidth = MAIN_WIDTH*0.8*0.125;
        codeCol.setPrefWidth(columnWidth);

        TableColumn nameCol = new TableColumn("Tên nhân viên");
        nameCol.setPrefWidth(columnWidth);

        TableColumn positionCol = new TableColumn("Chức danh");
        positionCol.setPrefWidth(columnWidth);

        TableColumn totalHoursCol = new TableColumn("Tổng giờ công");
        totalHoursCol.setPrefWidth(columnWidth);

        TableColumn totalShiftsCol = new TableColumn("Tổng ca làm");
        totalShiftsCol.setPrefWidth(columnWidth);

        TableColumn earlyCol = new TableColumn("Tổng về sớm");
        earlyCol.setPrefWidth(columnWidth);

        TableColumn lateCol = new TableColumn("Tổng đi muộn");
        lateCol.setPrefWidth(columnWidth);
        TableColumn<Object, Integer> indexColumn = new TableColumn<>("#");
        indexColumn.setCellValueFactory(cellData -> {
            int rowIndex = cellData.getTableView().getItems().indexOf(cellData.getValue());
            return (ObservableValue<Integer>) new PropertyValueFactory<>(String.valueOf(rowIndex+1));
        });


        tableView.getColumns().addAll(
                indexColumn,codeCol, nameCol,
                positionCol, totalHoursCol, totalShiftsCol,
                earlyCol, lateCol);
//        tableView.setPrefWidth(columnWidth*8);
        tableView.setPrefHeight(25* 15);
        setVgrow(tableView, Priority.ALWAYS);
        return tableView;
    }

    public List<OfficerSummaryRecord> getOfficerSummaryRecords() {
        return officerSummaryRecords;
    }

    public void setOfficerSummaryRecords(List<OfficerSummaryRecord> officerSummaryRecords) {
        this.officerSummaryRecords = officerSummaryRecords;
    }

    public List<WorkerSummaryRecord> getWorkerSummaryRecords() {
        return workerSummaryRecords;
    }

    public void setWorkerSummaryRecords(List<WorkerSummaryRecord> workerSummaryRecords) {
        this.workerSummaryRecords = workerSummaryRecords;
    }

    public void setDepartments(List<String> departments) {
        departmentBox.setItems(FXCollections.observableArrayList(departments));
    }
    public void setSummary(List<Summary> summaries){
        this.tableView.setItems(FXCollections.observableArrayList(summaries));
    }
}
