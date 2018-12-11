package ukeesTest.model;

public class Employee {

    private int employeeId;
    private String name;
    private boolean empActive;
    private int dpId;
    private Department department;

    public Employee(int employeeId, String name, boolean empActive, int dpId, Department department) {
        this.employeeId = employeeId;
        this.name = name;
        this.empActive = empActive;
        this.dpId = dpId;
        this.department = department;
    }

    public Employee() {
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEmpActive() {
        return empActive;
    }

    public void setEmpActive(boolean empActive) {
        this.empActive = empActive;
    }

    public int getDpId() {
        return dpId;
    }

    public void setDpId(int dpId) {
        this.dpId = dpId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee: \n" +
                "employeeId = " + employeeId +
                ", name = " + name + '\'' +
                ", empActive = " + empActive +
                ", dpId = " + dpId +
                ", department: " +department;
    }
}
