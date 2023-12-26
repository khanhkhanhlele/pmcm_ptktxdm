package hust.project.base.dashboard.Controller;

import hust.project.base.dashboard.View.Dashboard;

public class DasboardController {
    private final Dashboard dasboard = Dashboard.instance();
    public void init(){

    }
    private static DasboardController ins;
    private DasboardController() {
    }
    public static DasboardController instance() {
        if(ins == null){
            ins = new DasboardController();
        }
        return ins;
    }
}
