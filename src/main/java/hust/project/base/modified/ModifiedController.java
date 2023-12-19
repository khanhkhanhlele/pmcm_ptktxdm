package hust.project.base.modified;

import hust.project.base.employee_subsystem.HRService;
import hust.project.base.modified.View.PendingModifiedView;

public class ModifiedController {
    private PendingModifiedView pendingModifiedView;
//    private TableView<ModifiedDTO> requestTable;

    public ModifiedController(PendingModifiedView view) {
        this.pendingModifiedView = view;
        HRService service = new HRService();
//        pendingModifiedView.setDepartment(service.getDepartment());
    }


}
