package ukeesTest.model;

public class Department {

    private int id;
    private String depName;

    public Department(int id, String depName) {
        this.id = id;
        this.depName = depName;
    }

    public Department() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    @Override
    public String toString() {
        return "Department " +
                "id = " + id +
                " depName = " + depName;
    }
}
