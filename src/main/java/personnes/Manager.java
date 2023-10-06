package personnes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import utils.Coords;
import utils.Pictures;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manager extends Personne {

    private String department;

    public Manager() {
        super();
    }

    public Manager(String title, String lastName, String firstName, String gender, Pictures pictures, String address, Coords coords, String hobby, int birthYear, float salary,
                   float prime, String department) {
        super(title, lastName, firstName, gender, pictures, address, coords, hobby, birthYear, salary, prime);
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
