package hust.project.base.summary_logs.Controller;

import hust.project.base.employee_subsystem.HRService;
import hust.project.base.summary_logs.Model.SummaryRepository;
import hust.project.base.summary_logs.View.SummaryDepartmentView;

public class SummaryDepartmentController {
    private SummaryDepartmentView view;
    private SummaryRepository repository;

    public SummaryDepartmentController(SummaryDepartmentView view, SummaryRepository repository){
        this.view = view;
        this.repository = repository;
        HRService service = new HRService();
    }
}
