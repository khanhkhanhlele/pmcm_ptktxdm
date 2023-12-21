package hust.project.base.navbar;

import hust.project.base.constants.Route;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.util.Objects;

import static hust.project.base.constants.MetricsConstants.*;

public class Navbar extends BorderPane {
    private INavbarAction navbarAction;
    private static Navbar ins;
    private Navbar(){
        setPrefWidth(NAVBAR_WIDTH);
        setWidth(NAVBAR_WIDTH);
//        setStyle("-fx-background-color: #FFBE98");
        setTop(Top());
        setCenter(Center());
        setBottom(Bottom());
    }
    public static Navbar instance(){
        if(ins == null){
            ins = new Navbar();
        }
        return ins;
    }
    private VBox Top(){
        VBox vbox = new VBox();
        vbox.setStyle("-fx-background-color: #ffffff;  -fx-padding : 15px");
        vbox.setPrefHeight(APPLICATION_HEIGHT*0.1);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(APPLICATION_HEIGHT*0.01);
        vbox.setPrefWidth(NAVBAR_WIDTH);

        ImageView imageView = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/image2.png"))));
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        Label label = new Label("PHẦN MỀM CHẤM CÔNG");
        label.setPrefHeight(150);
        label.setStyle("-fx-font-size: 15px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #000000");
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_LEFT);
        hbox.setSpacing(10);
        hbox.getChildren().addAll(imageView, label);

        Line line = new Line(0, 0, NAVBAR_WIDTH, 0);

//        VBox vbox = new VBox();
        vbox.getChildren().addAll(hbox, line);
        return vbox;

    }
    private VBox Center(){
        VBox buttons = new VBox();
        buttons.setStyle("-fx-background-color: #FFFFF");
        buttons.setSpacing(10);
        buttons.setAlignment(Pos.TOP_CENTER);
        buttons.setPrefWidth(NAVBAR_WIDTH);
        buttons.setPrefHeight(APPLICATION_HEIGHT*0.4);

        String styledButton = "-fx-background-color: #989692;-fx-font-family: 'Arial'; -fx-text-fill: black; -fx-padding : 10px;   -fx-font-size: 16px";
        Button homeButton = new Button("  Màn hình chính");
        ImageView homeImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Home.png"))));
        homeImage.setFitHeight(20);
        homeImage.setFitWidth(20);
        homeButton.setGraphic(homeImage);
        homeButton.setStyle(styledButton);
        homeButton.setAlignment(Pos.CENTER_LEFT);
        homeButton.setPrefWidth(NAVBAR_WIDTH );
        homeButton.setPrefHeight(APPLICATION_HEIGHT*0.1);

        Button dashboardButton = new Button("  Nhập dữ liệu chấm công");
        ImageView dashboardImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Update.png"))));
        dashboardImage.setFitHeight(20);
        dashboardImage.setFitWidth(20);
        dashboardButton.setGraphic(dashboardImage);
        dashboardButton.setStyle(styledButton);
        dashboardButton.setAlignment(Pos.CENTER_LEFT);
        dashboardButton.setPrefWidth(NAVBAR_WIDTH );
        dashboardButton.setPrefHeight(APPLICATION_HEIGHT*0.1);


        Button summaryLogs = new Button("  Chấm công nhân viên");
        ImageView summaryImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Report.png"))));
        summaryImage.setFitHeight(20);
        summaryImage.setFitWidth(20);
        summaryLogs.setGraphic(summaryImage);
        summaryLogs.setPrefWidth(NAVBAR_WIDTH );
        summaryLogs.setPrefHeight(APPLICATION_HEIGHT*0.1);
        summaryLogs.setStyle(styledButton);
        summaryLogs.setAlignment(Pos.CENTER_LEFT);

        Button detailLogs = new Button("  Quản lý chấm công");
        ImageView detailImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Detail.png"))));
        detailImage.setFitHeight(20);
        detailImage.setFitWidth(20);
        detailLogs.setGraphic(detailImage);
        detailLogs.setPrefWidth(NAVBAR_WIDTH );
        detailLogs.setPrefHeight(APPLICATION_HEIGHT*0.1);
        detailLogs.setStyle(styledButton);
        detailLogs.setAlignment(Pos.CENTER_LEFT);

        Button modifiedLogs = new Button("  Duyệt yêu cầu chỉnh sửa");
        ImageView modifiedImage = new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/image/Request.png"))));
        modifiedImage.setFitHeight(20);
        modifiedImage.setFitWidth(20);
        modifiedLogs.setGraphic(modifiedImage);
        modifiedLogs.setPrefWidth(NAVBAR_WIDTH );
        modifiedLogs.setPrefHeight(APPLICATION_HEIGHT*0.1);
        modifiedLogs.setStyle(styledButton);
        modifiedLogs.setAlignment(Pos.CENTER_LEFT);

        homeButton.setOnAction(event -> navbarAction.navigate(Route.HOME_SCREEN));
        dashboardButton.setOnAction(event -> navbarAction.navigate(Route.DASHBOARD_SCREEN));
        summaryLogs.setOnAction(event -> navbarAction.navigate(Route.SUMMARY_DEPARTMENT_SCREEN));
        detailLogs.setOnAction(e -> System.out.println("Go to detailLogs"));
        modifiedLogs.setOnAction(e -> navbarAction.navigate(Route.MODIFIED_SCREEN));
        buttons.getChildren().addAll(homeButton, dashboardButton, summaryLogs, detailLogs, modifiedLogs);
        return buttons;
    }
    private BorderPane Bottom(){
        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background-color: #ffffff");
        pane.setPrefHeight(APPLICATION_HEIGHT * 0.15);
        Button detailLogs = new Button("Đăng Xuất");
        detailLogs.setPrefWidth(NAVBAR_WIDTH*0.99);
        detailLogs.setPrefHeight(APPLICATION_HEIGHT*0.1);
        detailLogs.setStyle("-fx-background-color: #C0C0C0;-fx-font-family: 'Arial'; -fx-text-fill: black; -fx-padding : 10px; -fx-font-weight: bold; -fx-font-size: 16px");
        pane.setCenter(detailLogs);
        return pane;
    }
    public void setNavbarAction(INavbarAction navbarAction) {
        this.navbarAction = navbarAction;
    }
}
