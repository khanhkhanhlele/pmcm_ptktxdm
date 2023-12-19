package hust.project.base.constants;

import javafx.stage.Screen;

public class MetricsConstants {
    public static final double APPLICATION_WIDTH = Screen.getPrimary().getVisualBounds().getWidth() * 0.9;
    public static final double APPLICATION_HEIGHT = Screen.getPrimary().getVisualBounds().getHeight() * 0.9;
    public static final double NAVBAR_WIDTH = APPLICATION_WIDTH * 0.2;
    public static final double MAIN_WIDTH = APPLICATION_WIDTH * 0.8;
    public static final double LAYOUT_X_MAIN = MAIN_WIDTH * 0.05;
    public static final String BG_COLOR_MAIN = "#ffffff";
}
