package data;
import utils.Constants;

public class ProgrammeurBean {
    private long id;
    private String lastName;
    private String firstName;
    private String address;
    private String pseudo;
    private String manager;
    private String hobby;
    private int birthYear;
    private float salary;
    private float prime;

    public ProgrammeurBean(String lastName, String firstName, String address, String pseudo, String manager, String hobby, int birthYear, float salary, float prime) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.address = address;
        this.pseudo = pseudo;
        this.manager = manager;
        this.hobby = hobby;
        this.birthYear = birthYear;
        this.salary = salary;
        this.prime = prime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public void setPrime(float prime) {
        this.prime = prime;
    }

    public long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getAddress() {
        return address;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getManager() {
        return manager;
    }

    public String getHobby() {
        return hobby;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public float getSalary() {
        return salary;
    }

    public float getPrime() {
        return prime;
    }

    @Override
    public String toString() {

        String columnFormat = "%-" + Constants.MSGLEN + "." + Constants.MSGLEN +"s";

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i<Constants.ATTRIBUTES.size(); i++){
            sb.append(String.format(columnFormat, Constants.ATTRIBUTES.get(i)));
            sb.append(" : ").append(Constants.ListAttributs(this).get(i));

            sb.append("\n");
        }

        return sb.toString();
    }
}
