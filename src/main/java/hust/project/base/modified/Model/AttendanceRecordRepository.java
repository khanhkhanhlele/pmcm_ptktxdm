package hust.project.base.modified.Model;

import java.util.List;

public interface AttendanceRecordRepository {

    AttendanceRecordDTO getAttendanceRecordByRecordId(String recordId);
    List<AttendanceRecordDTO> getAllAttendanceRecord();

    List<AttendanceRecordDTO> getAttendanceRecordByEmployeeId(String employeeId);

    void updateAttendanceRecord(String time, String recordId);

    String generateNextRecordId();

    void insertAttendanceRecord(AttendanceRecordDTO attendanceRecordDTO);

}
