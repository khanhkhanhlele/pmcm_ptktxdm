package hust.project.base.modified.Model;

public class ModifiedDTO {
    private String requestId;
    private String recordId;
    private String scanId; //khong can
    private String employeeId;
    private String date;
    private String time;
    private String timeModified;
    private String dateModified;
    private String requestReason;
    private String requestStatus;

    private String requestType;

    // Constructor
    public ModifiedDTO(String requestId, String recordId, String scanId, String employeeId, String date, String time, String timeModified, String dateModified, String requestReason, String requestStatus, String requestType) {
        this.requestId = requestId;
        this.recordId = recordId;
        this.scanId = scanId;
        this.employeeId = employeeId;
        this.date = date;
        this.time = time;
        this.timeModified = timeModified;
        this.dateModified = dateModified;
        this.requestReason = requestReason;
        this.requestStatus = requestStatus;
        this.requestType = requestType;
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
    public String getTimeModified() {
        return timeModified;
    }
    public void setTimeModified(String timeModified) {
        this.timeModified = timeModified;
    }
    public String getDateModified() {
        return dateModified;
    }
    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }
        public String getScanId() {
        return scanId;
    }
    public void setScanId(String scanId) {
        this.scanId = scanId;
    }
    public String getRequestId() {
        return requestId;
    }
    public void setRequestId(String requestId) {        this.requestId = requestId;    }
    public String getRequestReason() {
        return requestReason;
    }
    public void setRequestReason(String requestReason) {
        this.requestReason = requestReason;
    }
    public String getRequestStatus() {
        return requestStatus;
    }
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestType() {
        return requestType;
    }
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

}
