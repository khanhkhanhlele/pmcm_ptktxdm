package hust.project.base.navbar;

import hust.project.base.constants.Route;
import hust.project.base.dashboard.Dashboard;
import hust.project.base.home.HomeController;
import hust.project.base.modified.Model.ModifiedDAO;

import hust.project.base.modified.Model.ModifiedRepository;
import hust.project.base.summary_logs.SummaryDepartmentController;
import hust.project.base.summary_logs.SummaryDepartmentView;
import hust.project.base.modified.View.PendingModifiedView;
import hust.project.base.modified.ModifiedController;

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
                        HomeController.instance().changeScreen(SummaryDepartmentView.instance());
                        SummaryDepartmentView summaryDepartmentView = new SummaryDepartmentView();
                        new SummaryDepartmentController(summaryDepartmentView);
                        HomeController.instance().changeScreen(summaryDepartmentView);
                        System.out.println("navigated to Summary Department!");
                        break;
                    case DETAIL_SCREEN:
                        //TODO: navigate to detail screen
                        System.out.println("navigated to Detail!");
                        break;
                    case MODIFIED_SCREEN:
//                        AttendanceRecordRepository repository = new ModifiedDAO();
                        ModifiedRepository repository = new ModifiedDAO();
                        PendingModifiedView modifiedView = new PendingModifiedView(repository);
                        new ModifiedController(modifiedView);
                        HomeController.instance().changeScreen(modifiedView);
                        System.out.println("navigated to Modified!");
                        break;
                }
            }

            @Override
            public void navigate(String name, Object data) {

            }
        };
        navbar.setNavbarAction(navbarAction);
    }

}
