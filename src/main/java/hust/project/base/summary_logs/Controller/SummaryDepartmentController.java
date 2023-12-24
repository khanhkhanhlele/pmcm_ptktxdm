package hust.project.base.summary_logs.Controller;

import hust.project.base.summary_logs.Model.*;

import java.util.HashMap;
import java.util.List;

public class SummaryDepartmentController {
    private final SummaryRepository summaryRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;

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

    public HashMap<String, HashMap<String, Summary>> loadSummaryData() {
        return summaryRepository.loadSummaryData();
    }

    public List<Summary> updateTableForYear(String year) {
        return summaryRepository.updateTableForYear(year);
    }

    public List<Summary> updateTableForQuarter(String quarter) {
        return summaryRepository.updateTableForQuarter(quarter);
    }

    public String[] getAllPeriods() {
        return summaryRepository.getAllPeriods();
    }


}
