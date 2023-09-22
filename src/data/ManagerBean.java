package data;

public class ManagerBean extends Personne{

    private String department;

    public ManagerBean(){
        super();
    }
    public ManagerBean(String firstName, String lastName, String address, String hobby, int birthYear, float salary,
                       float prime, String department) {
        super(firstName, lastName, address, hobby, birthYear, salary, prime);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String toString(){
        StringBuilder json = new StringBuilder(super.toString());

        json.append("\t\"department\": \"").append(this.department).append("\"");
        json.append("\n}\n");

        return json.toString();
    }
}
