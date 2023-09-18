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

    public ProgrammeurBean(long id, String lastName, String firstName, String address, String pseudo, String manager, String hobby, int birthYear, float salary, float prime) {
        this.id = id;
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

        String columnFormat = "%-" + Constants.messageLen + "." + Constants.messageLen +"s";

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i<Constants.strings.size(); i++){
            sb.append(String.format(columnFormat, Constants.strings.get(i)));
            sb.append(" : ").append(Constants.ListAttributs(this).get(i));

            sb.append("\n");
        }

        return sb.toString();
    }
}
