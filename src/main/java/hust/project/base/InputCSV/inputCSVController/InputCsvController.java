package hust.project.base.InputCSV.inputCSVController;

import hust.project.base.InputCSV.inputCSVModel.AttendanceRecord;
import hust.project.base.InputCSV.inputCSVModel.InputAttendanceRecordRepository;
import hust.project.base.InputCSV.inputCSVView.InputCsvView;

import java.util.List;

public class InputCsvController {
    private final InputCsvView dashboard;
    private final InputAttendanceRecordRepository repository; // Thêm trường này

    public InputCsvController(InputCsvView dashboard, InputAttendanceRecordRepository repository) {
        this.dashboard = new InputCsvView(repository); ;
        this.repository = repository; // Tạo hoặc nhận instance của Repository
        initialize();
    }


    public void initialize() {
        List<AttendanceRecord> records = repository.getAllAttendanceRecord();
        dashboard.updateTable(records);
    }

}
