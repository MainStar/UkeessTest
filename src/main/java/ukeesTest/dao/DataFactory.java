package ukeesTest.dao;

import ukeesTest.model.Department;
import ukeesTest.model.Employee;
import ukeesTest.util.PropertyValues;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataFactory {

    private static Connection connection;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public static void connectToUkeessTestTable() throws ClassNotFoundException, SQLException, IOException {
        List<String> connectionProperties = PropertyValues.getDBPropertyValues();
        Class.forName(connectionProperties.get(0));
        connection = DriverManager.getConnection(connectionProperties.get(1), connectionProperties.get(2), connectionProperties.get(3));
        connection.setAutoCommit(false);
        statement = connection.createStatement();
    }

    public static void deleteEmployee(int empID) throws SQLException {
        System.out.println("Delete employee by ID '" + empID + "'");
        preparedStatement = connection.prepareStatement("delete from tblEmployees where empId = ?");
        preparedStatement.setInt(1, empID);
        preparedStatement.executeUpdate();
        connection.commit();
    }

    public static void editEmployee(Employee employee) throws SQLException {
        preparedStatement = connection.prepareStatement("update tblEmployees \n" +
                "cross join (select dpID as dpid from tblDepartments where dpName = ?) id " +
                "set empName = ?, empActive = ?, emp_dpID = id.dpid where empID = ?");
        preparedStatement.setString(1, employee.getDepartment().getDepName());
        preparedStatement.setString(2, employee.getName());
        preparedStatement.setBoolean(3, employee.isEmpActive());
        preparedStatement.setInt(4, employee.getEmployeeId());
        preparedStatement.executeUpdate();
        connection.commit();
    }

    public static int getEmployeesCount() throws SQLException {
        preparedStatement = connection.prepareStatement("select count(empID) from tblEmployees");
        resultSet = preparedStatement.executeQuery();
        int empCount = 0;
        while(resultSet.next()){
            empCount = resultSet.getInt(1);
        }
        return empCount;
    }

    public static List<Employee> getEmployeesByPage(int pageNumber, int employeesCount) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        preparedStatement = connection.prepareStatement(
                "select " +
                        "empID, " +
                        "empName, " +
                        "empActive, " +
                        "emp_dpID, " +
                        "dpName " +
                        "from tblEmployees left join\n" +
                        "tblDepartments on tblEmployees.emp_dpID = tblDepartments.dpID where empID between ? and ? order by empID asc");
        preparedStatement.setInt(1, Math.round((employeesCount / 10) * pageNumber - (employeesCount / 10)));
        preparedStatement.setInt(2, Math.round(employeesCount / 10) * pageNumber);
        resultSet = preparedStatement.executeQuery();

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
        preparedStatement = connection.prepareStatement(
                "select " +
                        "empID, " +
                        "empName, " +
                        "empActive, " +
                        "emp_dpID, " +
                        "dpName " +
                        "from tblEmployees left join \n" +
                        "tblDepartments on tblDepartments.dpID = tblEmployees.emp_dpID where empID = ?");
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();

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
        preparedStatement = connection.prepareStatement(
                "select " +
                        "empID, " +
                        "empName, " +
                        "empActive, " +
                        "emp_dpID, " +
                        "dpName " +
                        "from tblEmployees inner join tblDepartments on tblEmployees.emp_dpID = tblDepartments.dpID where empName like ?");
        preparedStatement.setString(1, searchLetter + "%");
        resultSet = preparedStatement.executeQuery();
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
        preparedStatement.close();
        connection.close();
    }

}
