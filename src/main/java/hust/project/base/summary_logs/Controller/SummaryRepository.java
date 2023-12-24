package hust.project.base.summary_logs.Controller;

import hust.project.base.summary_logs.Model.AttendanceRecordRecord;
import hust.project.base.summary_logs.Model.Summary;

import java.util.HashMap;
import java.util.List;

public interface SummaryRepository {
    public int calculateTotalSessions(List<AttendanceRecordRecord> records, String period);
    public double calculateTotalLateHours(List<AttendanceRecordRecord> records, String period);
    public double calculateTotalEarlyDepartures(List<AttendanceRecordRecord> records, String period);

    public String[] getAllPeriods();

    HashMap<String, HashMap<String, Summary>> loadSummaryData();

    List<Summary> updateTableForQuarter(String quarter);

    List<Summary> updateTableForYear(String year);







}
