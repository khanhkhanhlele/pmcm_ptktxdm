package hust.project.base.modified.View;
import hust.project.base.employee_subsystem.IHRService;
import hust.project.base.modified.Model.ModifiedDTO;
import hust.project.base.modified.Model.ModifiedRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.util.Callback;

import java.util.List;
import java.util.stream.Collectors;

import static hust.project.base.constants.MetricsConstants.MAIN_WIDTH;
public class PendingModifiedView extends VBox  {

    private IHRService hrService;
    private static ModifiedRepository modifiedRepository;

    private static PendingModifiedView ins;

    private TableView<ModifiedDTO> requestTable;
    public static PendingModifiedView instance(){
        if(ins == null){
            ins = new PendingModifiedView(modifiedRepository);
        }
        return ins;
    }

    public  PendingModifiedView(ModifiedRepository repo){
        this.modifiedRepository = repo;
        setSpacing(20);
        setPrefWidth(MAIN_WIDTH * 0.7);
        setMaxWidth(MAIN_WIDTH * 0.84);
        Label label = new Label("Danh sách các yêu cầu đang chờ");
        label.setStyle("-fx-font-size: 20px;");
        getChildren().addAll(label, createRequestTable());
    }
    private TableView<ModifiedDTO> createRequestTable() {
        TableView<ModifiedDTO> requestTable = new TableView<>();

        if (modifiedRepository == null) {
            System.out.println("modifiedRepository is null!");
            return null;
        }
        List<ModifiedDTO> modifiedDTOList = modifiedRepository.getAllModifiedDTO();
        requestTable.setItems(FXCollections.observableArrayList(modifiedDTOList));
//        requestTable.setEditable(true);
        requestTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!= null) {
                System.out.println(newValue.toString());
            }
        });
        TableColumn<ModifiedDTO, Number> sttCol = new TableColumn<>("STT");
        sttCol.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(requestTable.getItems().indexOf(column.getValue()) + 1));

        TableColumn<ModifiedDTO, String> employeeIdCol = new TableColumn<>("Nhân viên");
        employeeIdCol.setCellValueFactory(new PropertyValueFactory<>("employeeId"));

        TableColumn<ModifiedDTO, String> dateCol = new TableColumn<>("Ngày checkin");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<ModifiedDTO, String> timeCol = new TableColumn<>("Giờ checkin");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<ModifiedDTO, String> timeModifiedCol = new TableColumn<>("Giờ duyệt");
        timeModifiedCol.setCellValueFactory(new PropertyValueFactory<>("timeModified"));
        timeModifiedCol.setMaxWidth (100);

        TableColumn<ModifiedDTO, String> dateModifiedCol = new TableColumn<>("Ngày duyệt");
        dateModifiedCol.setCellValueFactory(new PropertyValueFactory<>("dateModified"));
        dateModifiedCol.setMaxWidth (100);

        TableColumn<ModifiedDTO, String> requestReasonCol = new TableColumn<>("Ghi chú");
        requestReasonCol.setCellValueFactory(new PropertyValueFactory<>("requestReason"));
        requestReasonCol.setMaxWidth(200);

        TableColumn<ModifiedDTO, String> requestStatusCol = new TableColumn<>("Trạng thái");
        requestStatusCol.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
        requestStatusCol.setCellFactory(column -> {
            return new TableCell<ModifiedDTO, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(item);

                        if (item.equals("Accepted")) {
                            setStyle("-fx-text-fill: green;");
                        } else if (item.equals("Rejected")) {
                            setStyle("-fx-text-fill: red;");
                        } else if (item.equals("Pending")) {
                            setStyle("-fx-text-fill: orange;"); // Using orange for better visibility instead of yellow
                        } else {
                            setStyle("");
                        }
                    }
                }
            };
        });

        TableColumn<ModifiedDTO, Void> actionCol = new TableColumn<>("Hành động");
        // Assume cellFactory is defined elsewhere
        actionCol.setCellFactory(cellFactory);
        requestTable.getColumns().addAll(sttCol, employeeIdCol, timeCol,dateCol,requestReasonCol, requestStatusCol, timeModifiedCol, dateModifiedCol,  actionCol);
        requestTable.setPrefHeight(25 * 18);
        requestTable.setItems(FXCollections.observableArrayList(modifiedDTOList));
        return requestTable;
    }
        Callback<TableColumn<ModifiedDTO, Void>, TableCell<ModifiedDTO, Void>> cellFactory = new Callback<>() {
        @Override
        public TableCell<ModifiedDTO, Void> call(final TableColumn<ModifiedDTO, Void> param) {
            final TableCell<ModifiedDTO, Void> cell = new TableCell<>() {

                private final Button btn = new Button("READ");
                {
                    btn.setStyle(" -fx-background-color: transparent;-fx-text-fill: blue;-fx-font-weight: bold;");
                }

                {
                    btn.setOnAction((ActionEvent event) -> {
                        ModifiedDTO data = getTableView().getItems().get(getIndex());
                        openModidiedView(data);
                    });
                }
                {
                    btn.setOnAction((ActionEvent event) -> {
                        ModifiedDTO data = getTableView().getItems().get(getIndex());
                        openModidiedView(data);
                    });
                }
                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(btn);
                    }
                }
            };
            return cell;
        }
    };

    private void openModidiedView(ModifiedDTO data) {
        ModifiedView modifiedView = new ModifiedView();
        modifiedView.display(data);
    }

}
