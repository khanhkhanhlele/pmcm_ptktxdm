package hust.project.base.summary_logs.Model;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRecordRepository {

    List<AttendanceRecordRecord> getAttendanceRecordByEmployeeId(String employeeId);

    public LocalDate getStartDateFromDB();
}
