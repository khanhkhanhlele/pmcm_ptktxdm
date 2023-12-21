package hust.project.base.summary_logs.Controller;

import hust.project.base.summary_logs.Model.*;

import java.util.List;

public class SummaryDepartmentController {
    private SummaryRepository summaryRepository;
    private AttendanceRecordRepository attendanceRecordRepository;

    public SummaryDepartmentController(SummaryRepository summaryRepository, AttendanceRecordRepository attendanceRecordRepository) {
        this.summaryRepository = summaryRepository;
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    public int calculateTotalSessions(String employeeId, String period) {
        List<AttendanceRecordRecord> records = attendanceRecordRepository.getAttendanceRecordByEmployeeId(employeeId);
        return summaryRepository.calculateTotalSessions(records, period);
    }

    public double calculateTotalLateHours(String employeeId, String period) {
        List<AttendanceRecordRecord> records = attendanceRecordRepository.getAttendanceRecordByEmployeeId(employeeId);
        return summaryRepository.calculateTotalLateHours(records, period);
    }

    public double calculateTotalEarlyDepartures(String employeeId, String period) {
        List<AttendanceRecordRecord> records = attendanceRecordRepository.getAttendanceRecordByEmployeeId(employeeId);
        return summaryRepository.calculateTotalEarlyDepartures(records, period);
    }
}
