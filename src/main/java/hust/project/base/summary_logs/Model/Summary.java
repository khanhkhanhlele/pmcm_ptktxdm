package hust.project.base.summary_logs.Model;

public class Summary {
    private String employeeName;
    private String employeeId;
    private String departmentName;
    private String period; // Tháng, quý, hoặc năm

    private int totalSessions; // Tổng số buổi đi làm
    private double totalLateHours; // Tổng số giờ đi muộn
    private double totalEarlyDepartures; // Tổng số giờ về sớm


    public Summary(String employeeName, String employeeId, String departmentName,
                   String period, int totalSessions, double totalLateHours, double totalEarlyDepartures) {
        this.employeeName = employeeName;
        this.employeeId = employeeId;
        this.departmentName = departmentName;
        this.period = period;
        this.totalSessions = totalSessions;
        this.totalLateHours = totalLateHours;
        this.totalEarlyDepartures = totalEarlyDepartures;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
    public int getTotalSessions() { return totalSessions; }
    public void setTotalSessions(int totalSessions) { this.totalSessions = totalSessions; }
    public double getTotalLateHours() { return totalLateHours; }
    public void setTotalLateHours(double totalLateHours) { this.totalLateHours = totalLateHours; }
    public double getTotalEarlyDepartures() { return totalEarlyDepartures; }
    public void setTotalEarlyDepartures(double totalEarlyDepartures) { this.totalEarlyDepartures = totalEarlyDepartures; }

}