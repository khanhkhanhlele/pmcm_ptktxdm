package hust.project.base.modified.View;

import hust.project.base.modified.Controller.ModifiedController;
import hust.project.base.modified.Model.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.util.Callback;

import java.util.List;
import java.util.function.Consumer;

import static hust.project.base.constants.MetricsConstants.MAIN_WIDTH;

public class PendingModifiedView extends VBox {
    private TableView<ModifiedDTO> requestTable;

    private static PendingModifiedView ins;

    public static PendingModifiedView instance() {
        if(ins == null){
            ins = new PendingModifiedView ();
        }
        return ins;
    }

    public PendingModifiedView() {
        initializeComponents();
    }
    private void initializeComponents() {
        setSpacing(20);
        setPrefWidth(MAIN_WIDTH * 0.7);
        setMaxWidth(MAIN_WIDTH * 0.84);
        Label label = new Label("Danh sách các yêu cầu đang chờ");
        label.setStyle("-fx-font-size: 20px;");
        requestTable = createRequestTable();
        getChildren().addAll(label, requestTable);

    }

    private TableView<ModifiedDTO> createRequestTable() {
        TableView<ModifiedDTO> table = new TableView<>();
        table.setEditable(true);
        TableColumn<ModifiedDTO, Number> sttCol = new TableColumn<>("STT");
        sttCol.setMinWidth(35);
        sttCol.setCellValueFactory(column -> new ReadOnlyObjectWrapper<>(table.getItems().indexOf(column.getValue()) + 1));
        sttCol.setSortable(false);
        table.getColumns().addAll(
                sttCol,
                createColumn("Nhân viên", "employeeId", String.class),
                createColumn("Ngày checkin", "date", String.class),
                createColumn("Giờ checkin", "time", String.class),
                createColumn("Giờ duyệt", "timeModified", String.class, 100),
                createColumn("Ngày duyệt", "dateModified", String.class, 100),
                createColumn("Ghi chú", "requestReason", String.class, 200),
                createStatusColumn()

        );
        table.setPrefHeight(25 * 18);
        return table;
    }

    private <T> TableColumn<ModifiedDTO, T> createColumn(String title, String property, Class<T> type, double... maxWidth) {
        TableColumn<ModifiedDTO, T> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        if (maxWidth.length > 0) {
            column.setMaxWidth(maxWidth[0]);
        }
        return column;
    }

    private TableColumn<ModifiedDTO, String> createStatusColumn() {
        TableColumn<ModifiedDTO, String> statusCol = new TableColumn<>("Trạng thái");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("requestStatus"));
        statusCol.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                if (item != null) {
                    setStyle("-fx-text-fill: " + switch (item) {
                        case "Accepted" -> "green";
                        case "Rejected" -> "red";
                        case "Pending" -> "orange";
                        default -> "black";
                    } + ";");
                }
            }
        });
        return statusCol;
    }

    public void createActionColumn(Consumer<ModifiedDTO> displayAction) {
        TableColumn<ModifiedDTO, Void> actionCol = new TableColumn<>("Actions");
        actionCol.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("READ");
            {
                btn.setStyle("-fx-background-color: transparent; -fx-text-fill: blue; -fx-font-weight: bold;");
                btn.setOnAction(event -> {
                    ModifiedDTO data = getTableView().getItems().get(getIndex());
                    if (data != null) {
                        displayAction.accept(data);
                    }
                });
            }
            @Override
            public void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
        requestTable.getColumns().add(actionCol);
    }

    public void updateTable(List<ModifiedDTO> data) {
        requestTable.setItems(FXCollections.observableArrayList(data));
    }

}
