package ukeesTest.dao;


import ukeesTest.model.Department;
import ukeesTest.model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataFactory {

    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public static void connectToUkeessTestTable() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/UkeessTest", "root", "root");
        connection.setAutoCommit(false);
        statement = connection.createStatement();
    }

    public static void deleteEmployee(int empID) {
        try {
            statement.executeUpdate("delete from tblEmployees where empID=" + empID);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public static void editEmployee(Employee employee) throws SQLException {
        String query = "update tblEmployees \n" +
                "cross join (select dpID as dpid from tblDepartments where dpName = '" + employee.getDepartment().getDepName() +"') id " +
                "set empName = '" + employee.getName() + "', empActive = " + employee.isEmpActive() + ", emp_dpID = id.dpid where empID = " + employee.getEmployeeId();
        System.out.println("Query edit: " + query);
        statement.executeUpdate(query);
        connection.commit();
    }

    public static List<Employee> getEmployeesByPage(int page) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        resultSet = statement.executeQuery("select *, tblDepartments.dpName from tblEmployees left join\n" +
                "tblDepartments on tblEmployees.emp_dpID where empID >= " + ((page * 5) - 4) + " limit " + "5");
        while (resultSet.next()){
            Employee employee = new Employee();
            Department department = new Department();
            employee.setEmployeeId(resultSet.getInt("empID"));
            employee.setName(resultSet.getString("empName"));
            employee.setEmpActive(resultSet.getBoolean("empActive"));
            employee.setDpId(resultSet.getInt("emp_dpID"));
            department.setDepName(resultSet.getString("dpName"));
            employee.setDepartment(department);
            employees.add(employee);
            System.out.println(employee);
        }
        return employees;
    }

    public static Employee getEmployeeById(int id) throws SQLException {
        resultSet = statement.executeQuery("select *, tblDepartments.dpName from tblEmployees left join\n" +
                                "tblDepartments on tblDepartments.dpID = tblEmployees.emp_dpID where empID =" + id);
        Employee employee = new Employee();
        Department department = new Department();
        while (resultSet.next()){
            employee.setEmployeeId(resultSet.getInt("empID"));
            employee.setName(resultSet.getString("empName"));
            employee.setEmpActive(resultSet.getBoolean("empActive"));
            employee.setDpId(resultSet.getInt("emp_dpID"));
            department.setDepName(resultSet.getString("dpName"));
            employee.setDepartment(department);
            System.out.println(employee);
        }
        return employee;
    }

    public static List<Employee> searchByFragment(String searchLetter) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        System.out.println("Getting employees from table by fragment");
        String query = "select * from tblEmployees inner join tblDepartments on tblEmployees.emp_dpID = tblDepartments.dpID where empName like'" + searchLetter + "%'";
        System.out.println(query);
        resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            Employee employee = new Employee();
            Department department = new Department();
            employee.setEmployeeId(resultSet.getInt("empID"));
            employee.setName(resultSet.getString("empName"));
            employee.setEmpActive(resultSet.getBoolean("empActive"));
            employee.setDpId(resultSet.getInt("emp_dpID"));
            department.setDepName(resultSet.getString("dpName"));
            employees.add(employee);
            employee.setDepartment(department);
            System.out.println(employee);
        }
        return employees;
    }

    public static void closeConnection() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }

}
