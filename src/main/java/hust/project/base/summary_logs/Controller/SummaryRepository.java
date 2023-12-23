package hust.project.base.summary_logs.Controller;

import hust.project.base.summary_logs.Model.AttendanceRecordRecord;

import java.util.List;

public interface SummaryRepository {
    public int calculateTotalSessions(List<AttendanceRecordRecord> records, String period);
    public double calculateTotalLateHours(List<AttendanceRecordRecord> records, String period);
    public double calculateTotalEarlyDepartures(List<AttendanceRecordRecord> records, String period);

}
