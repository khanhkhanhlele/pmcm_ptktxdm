package hust.project.base.modified.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hust.project.base.utils.sql_hikari.DatabaseManager;
import hust.project.base.utils.sql_hikari.SQLJavaBridge;

import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.text.SimpleDateFormat;
import java.util.Date;
public class ModifiedDAO implements ModifiedRepository {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";

    public List<ModifiedDTO> getAllModifiedDTO() {
        try {
            SQLJavaBridge bridge = DatabaseManager.instance ().defaulSQLJavaBridge ();
            String query = "SELECT request_id, record_id, scan_id, employee_id, date, time, time_modified, date_modified, request_reason, request_status, request_type FROM pmchamcong.modifiedattendancerecords";
            JsonArray json = bridge.query (query);
            List<ModifiedDTO> modifiedRequests = new ArrayList<> ();
            for (JsonElement element : json) {
                JsonObject obj = element.getAsJsonObject ();
                String requestId = obj.get ("request_id").getAsString ();
                String recordId = obj.has("record_id") && !obj.get("record_id").isJsonNull() ? obj.get("record_id").getAsString() : "******";
                String scanId = obj.has("scan_id") && !obj.get("scan_id").isJsonNull() ? obj.get("scan_id").getAsString() : "******";
                String employeeId = obj.get ("employee_id").getAsString ();
                String date = obj.get ("date").getAsString ();
                String time = obj.get ("time").getAsString ();
                String timeModified = obj.has ("time_modified") && !obj.get ("time_modified").isJsonNull () ? obj.get ("time_modified").getAsString () : "******";
                String dateModified = obj.has ("date_modified") && !obj.get ("date_modified").isJsonNull () ? obj.get ("date_modified").getAsString () : "******";
                String requestReason = obj.get ("request_reason").getAsString ();
                String requestStatus = obj.get ("request_status").getAsString ();
                String requestType = obj.get("request_type").getAsString ();
                ModifiedDTO modifiedDTO = new ModifiedDTO (requestId, recordId, scanId, employeeId, date, time, timeModified, dateModified, requestReason, requestStatus, requestType);
                modifiedRequests.add (modifiedDTO);
            }
            return modifiedRequests;
        } catch (Exception e) {
            e.printStackTrace ();
            return new ArrayList<> ();
        }
    }
    public void updateAcceptModifiedStatus(String requestId) {
        SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
        try {

            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String query2 = "UPDATE modifiedattendancerecords SET request_status = 'Accepted', date_modified = ?, time_modified = ? WHERE request_id = ?";
            bridge.update(query2, currentDate, currentTime, requestId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateAcceptModifiedRecordId(String requestId, String recordId) {
        SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
        try {

            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String query2 = "UPDATE modifiedattendancerecords SET request_status = 'Accepted', date_modified = ?, time_modified = ?, record_id = ? WHERE request_id = ?";
            bridge.update(query2, currentDate, currentTime, recordId ,requestId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void updateRejectModifiedStatus(String requestId) {
        SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
        try {

            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String query2 = "UPDATE modifiedattendancerecords SET request_status = 'Rejected', date_modified = ?, time_modified = ? WHERE request_id = ?";
            bridge.update(query2, currentDate, currentTime, requestId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
