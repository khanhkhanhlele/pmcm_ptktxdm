package hust.project.base.employee_subsystem;

import java.util.List;

public interface IHRService {
    public Employee getEmployee(String employeeId);
    public Department getDepartment(String departmentId);
    public List<Employee> getAllEmployee();
    public Department getDepartmentById(String departmentId);
    public List<Department> getAllDepartment();

}
