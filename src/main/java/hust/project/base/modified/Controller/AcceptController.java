package hust.project.base.modified.Controller;

import hust.project.base.modified.Model.*;
import hust.project.base.modified.View.AcceptView;
public class AcceptController {
    private AcceptView view;
    private AttendanceRecordRepository attendanceRecordRepo;
    private ModifiedRepository modifiedRepo;

    public AcceptController(AcceptView view, AttendanceRecordRepository attendanceRecordRepo, ModifiedRepository modifiedRepo) {
        this.view = view;
        this.attendanceRecordRepo = attendanceRecordRepo;
        this.modifiedRepo = modifiedRepo;
        setupViewActions();
    }

    private void setupViewActions() {
        view.setOnConfirmAction (this::confirmAcceptance);
        view.setOnCancelAction(this::cancelAcceptance);
    }

    private void confirmAcceptance(ModifiedDTO modifiedDTO) {
        if (modifiedDTO != null) {
            handleModifiedDTO(modifiedDTO);
            view.close();
            System.out.println("Chấp nhận yêu cầu thành công");
        }
    }

    private void cancelAcceptance(ModifiedDTO modifiedDTO) {
        if (modifiedDTO != null) {
            System.out.println("Hủy yêu cầu thành công");
            view.close();
        }
    }

    private void handleModifiedDTO(ModifiedDTO modifiedDTO) {
        if ("Chỉnh sửa chấm công".equals(modifiedDTO.getRequestType())) {
            attendanceRecordRepo.updateAttendanceRecord(modifiedDTO.getTime(), modifiedDTO.getRecordId());
            modifiedRepo.updateAcceptModifiedStatus(modifiedDTO.getRequestId());
        } else if ("Thêm chấm công".equals(modifiedDTO.getRequestType())) {
            String newRecordId = attendanceRecordRepo.generateNextRecordId();
            AttendanceRecordDTO attendanceRecordDTO = new AttendanceRecordDTO();
            attendanceRecordDTO.setRecordId(newRecordId);
            attendanceRecordDTO.setEmployeeId(modifiedDTO.getEmployeeId());
            attendanceRecordDTO.setDate(modifiedDTO.getDate());
            attendanceRecordDTO.setTime(modifiedDTO.getTime());
            attendanceRecordDTO.setFingerscannerId("0");
            attendanceRecordRepo.insertAttendanceRecord(attendanceRecordDTO);
            modifiedRepo.updateAcceptModifiedRecordId(modifiedDTO.getRequestId(), newRecordId);
        }
    }

}
