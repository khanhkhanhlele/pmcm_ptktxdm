package hust.project.base.modified.Controller;

import hust.project.base.modified.Model.AttendanceRecordRepository;
import hust.project.base.modified.Model.ModifiedDTO;
import hust.project.base.modified.Model.ModifiedRepository;
import hust.project.base.modified.View.AcceptView;
import hust.project.base.modified.View.ModifiedView;
import hust.project.base.modified.View.RejectView;


public class ModifiedController{
    private ModifiedView view;
    private ModifiedRepository modifiedRepository;

    private AttendanceRecordRepository attendanceRecordRepository;

    public ModifiedController(ModifiedView view, ModifiedRepository modifiedRepository, AttendanceRecordRepository attendanceRecordRepository) {
        this.view = view;
        this.modifiedRepository = modifiedRepository;
        this.attendanceRecordRepository = attendanceRecordRepository;
        setupViewActions();

    }
    public void setupViewActions() {
        view.setOnAcceptAction(this::handleAccept);
        view.setOnRejectAction(this::handleReject);
        view.setOnCancelAction(this::handleCancel);
    }

  private void handleAccept(ModifiedDTO data) {
        if (data != null) {
            AcceptView acceptView = new AcceptView();
            view.close ();
            new AcceptController(acceptView, attendanceRecordRepository, modifiedRepository);
            acceptView.display(data);

        }
    }
    private void handleReject(ModifiedDTO data) {
        if (data != null) {
            RejectView rejectView = new RejectView ();
            view.close ();
            new RejectController(rejectView, modifiedRepository);
            rejectView.display(data);
        }
    }
    private void handleCancel (ModifiedDTO data){
            view.close ();
    }

}
