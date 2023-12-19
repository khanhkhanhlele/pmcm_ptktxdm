package hust.project.base.dashboard;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class Dashboard extends BorderPane {
    private static Dashboard ins;
    private Dashboard () {
        Label label = new Label("DASHBOARD SCREEN");
        label.setStyle("-fx-font-size : 70px");
        label.setPrefHeight(500);
        label.setPrefHeight(500);
        setCenter(label);
    }
    public static Dashboard instance(){
        if(ins ==  null){
            ins = new Dashboard();
        }
        return ins;
    }

}
