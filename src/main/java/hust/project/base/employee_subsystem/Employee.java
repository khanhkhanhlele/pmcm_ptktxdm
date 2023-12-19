package hust.project.base.employee_subsystem;

public class Employee {
    private String employeeId; // Changed from int to String to match char type in DB
    private String name;
    private String departmentId; // Added field
    private String employeeType; // Added field

    // Constructor
    public Employee(String employeeId, String name, String departmentId, String employeeType) {
        this.employeeId = employeeId;
        this.name = name;
        this.departmentId = departmentId;
        this.employeeType = employeeType;
    }

    // Default constructor
    public Employee() {
    }

    // Getters and setters
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }
}
