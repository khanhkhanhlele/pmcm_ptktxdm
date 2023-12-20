package hust.project.base.summary_logs.Model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hust.project.base.utils.sql_hikari.DatabaseManager;
import hust.project.base.utils.sql_hikari.SQLJavaBridge;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SummaryDAO implements SummaryRepository{
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String TIME_FORMAT = "HH:mm:ss";

    @Override
    public List<SummaryDTO> getAllSummaryDTO() {
        try {
            SQLJavaBridge bridge = DatabaseManager.instance ().defaulSQLJavaBridge ();
            String query = "SELECT  * FROM pmchamcong.attendancerecords";
            JsonArray json = bridge.query (query);
            List<SummaryDTO> modifiedRequests = new ArrayList<> ();
            for (JsonElement element : json) {
                JsonObject obj = element.getAsJsonObject();
                String recordId = obj.get("record_id").getAsString();
                String employeeId = obj.get("employee_id").getAsString();
                String fingerscannerId = obj.get("fingerscanner_id").getAsString();
                String date = obj.get("date").getAsString();
                String time = obj.get("time").getAsString();

                SummaryDTO modifiedDTO = new SummaryDTO ( recordId, fingerscannerId, employeeId, date, time);
                modifiedRequests.add (modifiedDTO);
            }
            return modifiedRequests;
        } catch (Exception e) {
            e.printStackTrace ();
            return new ArrayList<> ();
        }
    }
}
