package hust.project.base.InputCSV.inputCSVModel;

import java.util.List;

public interface InputAttendanceRecordRepository {
    List<AttendanceRecord> getAllAttendanceRecord();
    void insertAttendanceRecord(AttendanceRecord attendanceRecordDTO);
}
