package personnes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import personnes.utils.Address;
import personnes.utils.Coords;
import personnes.utils.Pictures;
import utils.Departments;
import utils.Gender;
import utils.Hobbies;
import utils.Title;

import java.util.LinkedHashMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manager extends Personne {

    private Departments department;

    public Manager() {
        super();
    }

    public Manager(Title title, String lastName, String firstName, Gender gender, Pictures pictures, Address address, Coords coords, Hobbies hobby, int birthYear, float salary,
                   float prime, Departments department) {
        super(title, lastName, firstName, gender, pictures, address, coords, hobby, birthYear, salary, prime);
        this.department = department;
    }

    @JsonProperty("department")
    public Departments getDepartment() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department = department;
    }

    @Override
    public LinkedHashMap<String, Object> getColumns() {
        LinkedHashMap<String, Object> columns = super.getColumns();
        columns.put("Department", this.department.getDepartment());
        return columns;
    }
}
