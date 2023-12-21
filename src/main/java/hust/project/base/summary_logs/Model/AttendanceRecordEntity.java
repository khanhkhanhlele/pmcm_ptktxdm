package hust.project.base.summary_logs.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hust.project.base.utils.sql_hikari.DatabaseManager;
import hust.project.base.utils.sql_hikari.SQLJavaBridge;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecordEntity implements AttendanceRecordRepository {

    public List<AttendanceRecordRecord> getAttendanceRecordByEmployeeId(String employeeId) {
        SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
        List<AttendanceRecordRecord> records = new ArrayList<>();
        try {
            String query = "SELECT * FROM pmchamcong.attendancerecords WHERE employee_id = ?";
            JsonArray jsonArray = bridge.query(query, employeeId);
            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                String recordId = obj.get("record_id").getAsString();
                String fingerscannerId = obj.get("fingerscanner_id").getAsString();
                String date = obj.get("date").getAsString();
                String time = obj.get("time").getAsString();
                records.add(new AttendanceRecordRecord (recordId, employeeId, fingerscannerId, date, time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    public LocalDate getStartDateFromDB(){
        LocalDate startDate = LocalDate.now ();
        startDate =LocalDate.parse("01-01-2022", DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return startDate;
    }



//    public LocalDate getStartDateFromDB() {
//        SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
//        LocalDate startDate = LocalDate.now ();
//        try {
//            String query = "SELECT MIN(STR_TO_DATE(date, '%d-%m-%Y')) AS earliestDate FROM pmchamcong.attendancerecords";
//            JsonObject json = bridge.queryOne(query);
//
//            if (json != null && json.has("earliestDate") && !json.get("earliestDate").isJsonNull()) {
//                String earliestDateString = json.get("earliestDate").getAsString();
//                startDate = LocalDate.parse(earliestDateString, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return startDate;
//    }


}
