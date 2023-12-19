package hust.project.base.home;

import hust.project.base.header.SearchCallBack;
import hust.project.base.navbar.NavBarController;
import hust.project.base.header.Header;
import hust.project.base.navbar.Navbar;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

import static hust.project.base.constants.MetricsConstants.*;

public class Home extends HBox {
    private SearchCallBack searchCallBack;
    private Pane currentScreen;
    VBox vboxMain ;
    private static Home ins;
    public static Home instance(){
        if(ins == null){
            ins = new Home();
        }
        return ins;
    }

    private Home(){
        setStyle("-fx-background-color : " + BG_COLOR_MAIN);
        NavBarController.instance().init();
        Navbar navbar = Navbar.instance();
        getChildren().add(navbar);
        getChildren().add(Main());
    }
    private Pane Main(){
        Pane pane = new Pane();
        pane.setStyle("-fx-padding: 10px");
        vboxMain = new VBox();
        vboxMain.setSpacing(5);
        vboxMain.setStyle("-fx-padding: 10px;");
        vboxMain.setLayoutX(LAYOUT_X_MAIN);
        Header header = new Header();
        Line line = new Line(0,0,MAIN_WIDTH*0.7,0);
        currentScreen = HomeScreen();
        vboxMain.getChildren().addAll(header, line, currentScreen);
        pane.getChildren().add(vboxMain);

        return pane;
    }
    public void setCurrentScreen(Pane body){
        System.out.println("current screen ..." + (currentScreen.getClass().getName()));
        System.out.println("changing screen ..." + (body.getClass().getName()));
        if(currentScreen.equals(body)){
            return;
        }
        this.currentScreen.setVisible(false);
        vboxMain.getChildren().remove(currentScreen);
        vboxMain.getChildren().add(body);

        this.currentScreen = body;
        this.currentScreen.setVisible(true);
    }
    public void setCurrentScreen(){
        setCurrentScreen(HomeScreen());
    }

    private BorderPane HomeScreen(){
        BorderPane home = new BorderPane();
        Label homeLabel = new Label("HOME SCREEN");
        homeLabel.setStyle("-fx-font-size : 70px");
        homeLabel.setPrefHeight(500);
        home.setCenter(homeLabel);
        return home;
    }
}

