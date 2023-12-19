package hust.project.base.employee_subsystem;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import hust.project.base.utils.sql_hikari.DatabaseManager;
import hust.project.base.utils.sql_hikari.SQLJavaBridge;

import java.util.ArrayList;
import java.util.List;

public class HRService implements IHRService {
    public Employee getEmployee(String employeeId) {
        try {
            SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
            String query = "SELECT employee_id, name, department_id, employee_type FROM employees WHERE employee_id = ?";
            JsonObject json = bridge.queryOne(query, employeeId);
            String name = json.get("name").getAsString();
            String departmentId = json.get("department_id").getAsString();
            String employeeType = json.get("employee_type").getAsString();
            return new Employee(employeeId, name, departmentId, employeeType);
        } catch (Exception e) {
            e.printStackTrace();
            return new Employee();
        }
    }

    public Department getDepartment(String departmentId) {
        try {
            SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
            String query = "SELECT department_id, department_name FROM departments WHERE department_id = ?";
            JsonObject json = bridge.queryOne(query, departmentId);
            String departmentName = json.get("department_name").getAsString();
            return new Department(departmentId, departmentName);
        } catch (Exception e) {
            e.printStackTrace();
            return new Department();
        }
    }
//    get all employee
    public List<Employee> getAllEmployee(){
        try{
            SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
            String query = "SELECT employee_id, name, department_id, employee_type FROM employees";
            JsonArray json = bridge.query(query);
            List<Employee> employees = new ArrayList<>();
            for (JsonElement element : json) {
                String employeeId = element.getAsJsonObject().get("employee_id").getAsString();
                String name = element.getAsJsonObject().get("name").getAsString();
                String departmentId = element.getAsJsonObject().get("department_id").getAsString();
                String employeeType = element.getAsJsonObject().get("employee_type").getAsString();
                employees.add(new Employee(employeeId, name, departmentId, employeeType));
            }
            return employees;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
//get department by id
    public Department getDepartmentById(String departmentId){
        try{
            SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
            String query = "SELECT department_id, department_name FROM departments WHERE department_id = ?";
            JsonObject json = bridge.queryOne(query, departmentId);
            String departmentName = json.get("department_name").getAsString();
            return new Department(departmentId, departmentName);
        }catch (Exception e){
            e.printStackTrace();
            return new Department();
        }
    }


    public List<Department> getAllDepartment() {
        try {
            SQLJavaBridge bridge = DatabaseManager.instance().defaulSQLJavaBridge();
            String query = "SELECT department_id, department_name FROM departments";
            JsonArray json = bridge.query(query);
            List<Department> departments = new ArrayList<>();
            for (JsonElement element : json) {
                String departmentId = element.getAsJsonObject().get("department_id").getAsString();
                String departmentName = element.getAsJsonObject().get("department_name").getAsString();
                departments.add(new Department(departmentId, departmentName));
            }
            return departments;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
