package hust.project.base.navbar;

import hust.project.base.constants.Route;
import hust.project.base.dashboard.Dashboard;
import hust.project.base.home.HomeController;
import hust.project.base.modified.Model.ModifiedDAO;

import hust.project.base.modified.Model.ModifiedRepository;
import hust.project.base.summary_logs.Controller.SummaryDepartmentController;
import hust.project.base.summary_logs.Controller.SummaryRepository;
import hust.project.base.summary_logs.Controller.SummaryService;
import hust.project.base.summary_logs.Model.*;
import hust.project.base.summary_logs.View.SummaryDepartmentView;
import hust.project.base.modified.View.PendingModifiedView;
import hust.project.base.modified.Controller.PendingModifiedController;

public class NavBarController {
    private final Navbar navbar = Navbar.instance();
    public void init(){
        handleNavigation();
    }
    private static NavBarController ins;
    private NavBarController() {
    }
    public static NavBarController instance() {
        if(ins == null){
            ins = new NavBarController();
        }
        return ins;
    }
    private void handleNavigation(){
        INavbarAction navbarAction = new INavbarAction() {
            @Override
            public void navigate(Route name) {
                switch (name){
                    case HOME_SCREEN:
                        HomeController.instance().changeScreen(null);
                        System.out.println("navigated to Home!");
                        break;
                    case DASHBOARD_SCREEN:
                        HomeController.instance().changeScreen(Dashboard.instance());
                        System.out.println("navigated to Dashboard!");
                        break;
                    case SUMMARY_DEPARTMENT_SCREEN:
                        SummaryRepository summary = new SummaryService();
                        AttendanceRecordRepository attendanceRecord = new AttendanceRecordEntity ();
                        SummaryDepartmentController summaryController = new SummaryDepartmentController(summary, attendanceRecord);
                        SummaryDepartmentView summaryDepartmentView = new SummaryDepartmentView(summaryController);
                        HomeController.instance().changeScreen(summaryDepartmentView);
                        System.out.println("navigated to Summary Department!");
                        break;
                    case DETAIL_SCREEN:
                        //TODO: navigate to detail screen
                        System.out.println("navigated to Detail!");
                        break;
                    case MODIFIED_SCREEN:
                        ModifiedRepository repository = new ModifiedDAO();
                        PendingModifiedView modifiedView = PendingModifiedView.instance(); // Use the instance method if it's a singleton
                        new PendingModifiedController(modifiedView, repository);
                        HomeController.instance().changeScreen(modifiedView);
                        System.out.println("Navigated to Modified!");
                        break;

                }
            }

        };
        navbar.setNavbarAction(navbarAction);
    }

}
