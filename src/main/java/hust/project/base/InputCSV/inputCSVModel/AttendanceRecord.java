package hust.project.base.InputCSV.inputCSVModel;

public class AttendanceRecord {
    private String recordId;
    private String fingerscannerId;
    private String employeeId;
    private String date;
    private String time;


    // Constructor
    public AttendanceRecord( String recordId, String fingerscannerId, String employeeId, String date, String time) {
        this.recordId = recordId;
        this.employeeId = employeeId;
        this.date = date;
        this.time = time;
        this.fingerscannerId = fingerscannerId;
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

    public String getFingerscannerId() {
        return fingerscannerId;
    }

    public void setFingerscannerId(String fingerscannerId) {
        this.fingerscannerId = fingerscannerId;
    }
}
