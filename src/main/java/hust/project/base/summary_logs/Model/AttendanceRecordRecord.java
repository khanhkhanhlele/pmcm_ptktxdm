package hust.project.base.summary_logs.Model;

public class AttendanceRecordRecord {

    private String recordId;
    private String employeeId;
    private String fingerscannerId;
    private String date;
    private String time;

    public AttendanceRecordRecord(String recordId, String employeeId, String fingerscannerId, String date, String time) {
        this.recordId = recordId;
        this.employeeId = employeeId;
        this.fingerscannerId = fingerscannerId;
        this.date = date;
        this.time = time;
    }

    public AttendanceRecordRecord() {
    }
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFingerscannerId() {
        return fingerscannerId;
    }

    public void setFingerscannerId(String fingerscannerId) {
        this.fingerscannerId = fingerscannerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
