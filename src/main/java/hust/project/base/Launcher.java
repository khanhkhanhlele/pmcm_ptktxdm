package hust.project.base;

import hust.project.base.home.Home;
import hust.project.base.utils.sql_hikari.DatabaseManager;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static hust.project.base.constants.MetricsConstants.*;

public class Launcher extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        DatabaseManager.instance().init();
        Home home = Home.instance();
        Scene rootScene = new Scene(home, APPLICATION_WIDTH, APPLICATION_HEIGHT);
        stage.setScene(rootScene);
        stage.show();

    }
}