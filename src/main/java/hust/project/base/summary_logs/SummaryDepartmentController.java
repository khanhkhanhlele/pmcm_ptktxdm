package hust.project.base.summary_logs;

import hust.project.base.employee_subsystem.HRService;

public class SummaryDepartmentController {
    private SummaryDepartmentView summaryDepartmentView;
    public SummaryDepartmentController(SummaryDepartmentView view){
        this.summaryDepartmentView = view;

        HRService service = new HRService();
//        summaryDepartmentView.setDepartments(service.getListDepartments(7));
//        summaryDepartmentView.setSummary(service.getSummary());


    }


}
