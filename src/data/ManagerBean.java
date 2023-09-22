package data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManagerBean extends Personne {

    private String department;

    public ManagerBean() {
        super();
    }

    public ManagerBean(String firstName, String lastName, String address, String hobby, int birthYear, float salary,
                       float prime, String department) {
        super(firstName, lastName, address, hobby, birthYear, salary, prime);
        this.department = department;
    }

    @JsonProperty("department")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

}
