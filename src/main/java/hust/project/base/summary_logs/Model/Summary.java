package hust.project.base.summary_logs.Model;

import hust.project.base.employee_subsystem.Employee;

public class Summary {
    private int id;
    private int departmentId;
    private Employee employee;
    private int year;
    private int month;
    private int quarter;
    // Worker
    private double totalShiftHours = 0;
    // Officer
    private double totalSessions = 0;
    private double totalEarly = 0;
    private double totalLate = 0;
}
