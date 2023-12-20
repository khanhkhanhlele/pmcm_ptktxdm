package hust.project.base.summary_logs.Model;

import hust.project.base.modified.Model.AttendanceRecordDTO;

import java.util.List;

public interface AttendanceRecordRepository {
    AttendanceRecordDTO getAttendanceRecordByRecordId(String recordId);
    List<AttendanceRecordDTO> getAllAttendanceRecord();

    List<AttendanceRecordDTO> getAttendanceRecordByEmployeeId(String employeeId);

    void updateAttendanceRecord(String time, String recordId);

    String generateNextRecordId();

    void insertAttendanceRecord(AttendanceRecordDTO attendanceRecordDTO);
}
