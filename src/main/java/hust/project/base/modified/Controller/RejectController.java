package hust.project.base.modified.Controller;

import hust.project.base.modified.Model.ModifiedDTO;
import hust.project.base.modified.Model.ModifiedRepository;
import hust.project.base.modified.View.RejectView;

public class RejectController {
    private RejectView view;
    private ModifiedRepository modifiedRepo;

    public RejectController(RejectView view, ModifiedRepository modifiedRepo) {
        this.view = view;
        this.modifiedRepo = modifiedRepo;
        setupViewActions();
    }

    private void setupViewActions() {
        view.setOnConfirmAction(this::confirmRejection);
        view.setOnCancelAction(this::cancelAcceptance);
    }

    private void confirmRejection(ModifiedDTO modifiedDTO) {
        if (modifiedDTO != null) {
            modifiedRepo.updateRejectModifiedStatus(modifiedDTO.getRequestId());
            System.out.println("Từ chối thành công");
            view.close();
        }
    }

    private void cancelAcceptance(ModifiedDTO modifiedDTO) {
        if (modifiedDTO != null) {
            System.out.println("Hủy ");
            view.close();
        }
    }


}
