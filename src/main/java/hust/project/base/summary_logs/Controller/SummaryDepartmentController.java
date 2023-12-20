package hust.project.base.summary_logs.Controller;

import hust.project.base.employee_subsystem.HRService;
import hust.project.base.summary_logs.Model.SummaryDAO;
import hust.project.base.summary_logs.Model.SummaryDTO;
import hust.project.base.summary_logs.Model.SummaryRepository;
import hust.project.base.summary_logs.View.SummaryDepartmentView;

import javafx.event.ActionEvent;


import java.util.List;

public class SummaryDepartmentController {
    private SummaryDepartmentView view;
    private SummaryRepository repository;

    public SummaryDepartmentController(SummaryDepartmentView view, SummaryRepository repository){
        this.view = view;
        this.repository = repository;

        HRService service = new HRService();

        loadPendingRequests();
//        summaryDepartmentView.setDepartments(service.getListDepartments(7));
//        summaryDepartmentView.setSummary(service.getSummary());
    }
    private void loadPendingRequests() {
        if (this.repository == null) {
            System.out.println ("modifiedRepository is null");
        } else {
            List<SummaryDTO> pendingRequests = repository.getAllSummaryDTO();
            view.updateTable (pendingRequests);
        }
    }

}
