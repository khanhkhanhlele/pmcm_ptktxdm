package hust.project.base.employee_subsystem;

public class Department {
    private String departmentId; // Changed from int to String to match char(10) in DB
    private String departmentName; // Renamed to match 'department_name'

    // Constructor
    public Department(String departmentId, String departmentName) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    // Default constructor
    public Department() {
    }

    // Getters and setters
    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
}
