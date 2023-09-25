package data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Personne {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String hobby;
    private int birthYear;
    private float salary;
    private float prime;

    public Personne() {
        this.lastName = null;
        this.firstName = null;
        this.address = null;
        this.hobby = null;
        this.birthYear = 0;
        this.salary = 0;
        this.prime = 0;
    }

    public Personne(String firstName, String lastName, String address, String hobby, int birthYear, float salary, float prime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.hobby = hobby;
        this.birthYear = birthYear;
        this.salary = salary;
        this.prime = prime;
    }

    /**
     * Obtient l'identifiant unique de la personne.
     *
     * @return L'identifiant unique de la personne.
     */
    @JsonProperty("id")
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de la personne.
     *
     * @param id L'identifiant unique de la personne.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtient le nom du personne.
     *
     * @return Le nom du personne.
     */
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * Définit le nom de la personne.
     *
     * @param lastName Le nom de la personne.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtient le prénom de la personne.
     *
     * @return Le prénom de la personne.
     */
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * Définit le prénom de la personne.
     *
     * @param firstName Le prénom de la personne.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtient l'adresse de la personne.
     *
     * @return L'adresse de la personne.
     */
    @JsonProperty("address")
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse de la personne.
     *
     * @param address L'adresse de la personne.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Obtient le hobby de la personne.
     *
     * @return Le hobby de la personne.
     */
    @JsonProperty("hobby")
    public String getHobby() {
        return hobby;
    }

    /**
     * Définit le hobby de la personne.
     *
     * @param hobby Le hobby de la personne.
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * Obtient l'année de naissance de la personne.
     *
     * @return L'année de naissance de la personne.
     */
    @JsonProperty("birthYear")
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Définit l'âge de la personne en fonction de sa date de naissance
     * @return L'âge de la personne
     */
    @JsonProperty("age")
    public int getAge(){
        return LocalDate.now().getYear() - this.birthYear;

    }

    /**
     * Définit l'année de naissance de la personne.
     *
     * @param birthYear L'année de naissance de la personne.
     */
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    /**
     * Obtient le salaire de la personne.
     *
     * @return Le salaire de la personne.
     */
    @JsonProperty("salary")
    public float getSalary() {
        return salary;
    }

    /**
     * Définit le salaire de la personne.
     *
     * @param salary Le salaire de la personne.
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     * Obtient la prime de la personne.
     *
     * @return La prime de la personne.
     */
    @JsonProperty("prime")
    public float getPrime() {
        return prime;
    }

    /**
     * Définit la prime de la personne.
     *
     * @param prime La prime de la la personne.
     */
    public void setPrime(float prime) {
        this.prime = prime;
    }


    @Override
    public String toString() {
        return this.toJson();
    }

    public String toJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

            return objectMapper.writeValueAsString(this);
        } catch (Exception e) {
            return "{}";
        }
    }
}