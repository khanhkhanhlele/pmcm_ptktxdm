package hust.project.base.InputCSV.inputCSVModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hust.project.base.InputCSV.inputCSVController.InvalidController;
import hust.project.base.utils.sql_hikari.DatabaseManager;
import hust.project.base.utils.sql_hikari.SQLJavaBridge;

import java.util.ArrayList;
import java.util.List;

public class InputAttendanceRecordEntity implements InputAttendanceRecordRepository {

    public List<AttendanceRecord> getAllAttendanceRecord() {
        SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
        List<AttendanceRecord> records = new ArrayList<> ();
        try {
            String query = "SELECT * FROM attendancerecords";
            JsonArray jsonArray = bridge.query(query);
            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                String recordId = obj.get("record_id").getAsString();
                String employeeId = obj.get("employee_id").getAsString();
                String fingerscannerId = obj.get("fingerscanner_id").getAsString();
                String date = obj.get("date").getAsString();
                String time = obj.get("time").getAsString();
                records.add(new AttendanceRecord(recordId, employeeId, fingerscannerId, date, time));
            }
        } catch (Exception e) {
            // Log or handle the exception
        }
        return records;
    }

    public void insertAttendanceRecord(AttendanceRecord attendanceRecordDTO) {
        SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
        try {
            String query = "INSERT INTO attendancerecords (record_id, employee_id, fingerscanner_id, date, time) VALUES (?, ?, ?, ?, ?)";
            bridge.update(query, attendanceRecordDTO.getRecordId(), attendanceRecordDTO.getEmployeeId(), attendanceRecordDTO.getFingerscannerId () , attendanceRecordDTO.getDate(), attendanceRecordDTO.getTime());
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Duplicate entry" + attendanceRecordDTO.getRecordId() + " in database");
            }
            String errorTitle = "Invalid #" + e.hashCode(); // or a more relevant error code
            InvalidController.showError(errorTitle, e.getMessage());
        }
    }
}
