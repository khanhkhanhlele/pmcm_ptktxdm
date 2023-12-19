package hust.project.base.summary_logs;

import hust.project.base.employee_subsystem.Employee;

public abstract class SummaryRecord {
    private int id;
    private int departmentId;
    private Employee employee;
    private int year;
    private int month;
    private int quarter;

    public SummaryRecord(int id, int departmentId, Employee employee, int year, int month, int quarter) {
        this.id = id;
        this.departmentId = departmentId;
        this.employee = employee;
        this.year = year;
        this.month = month;
        this.quarter = quarter;
    }

    public SummaryRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getQuarter() {
        return quarter;
    }

    public void setQuarter(int quarter) {
        this.quarter = quarter;
    }
}
