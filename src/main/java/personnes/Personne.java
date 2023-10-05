package personnes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.time.LocalDate;

@JsonPropertyOrder({"picture", "id", "firstName", "lastName", "gender", "age", "address", "hobby", "birthYear", "salary", "prime"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Personne {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String picture;
    private String address;
    private String hobby;
    private int birthYear;
    private float salary;
    private float prime;

    public Personne() {
        this.lastName = null;
        this.firstName = null;
        this.gender = null;
        this.picture = null;
        this.address = null;
        this.hobby = null;
        this.birthYear = 0;
        this.salary = 0;
        this.prime = 0;
    }

    public Personne(String lastName, String firstName, String gender, String picture, String address, String hobby, int birthYear, float salary, float prime) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.picture = picture;
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
        return this.id;
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
     * Obtient le nom de la personne.
     *
     * @return Le nom de la personne.
     */
    @JsonProperty("lastName")
    public String getLastName() {
        return this.lastName;
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
        return this.firstName;
    }

    public String getFullName() {
        return this.lastName.toUpperCase() + " " + this.firstName;
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
     * Obtient le genre de la personne.
     *
     * @return Le genre de la personne.
     */
    @JsonProperty("gender")
    public String getGender() {
        return this.gender;
    }

    /**
     * Définit le genre de la personne.
     *
     * @param gender Le genre de la personne.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("picture")
    public String getPicture(){
        return this.picture;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }
    /**
     * Définit l'âge de la personne en fonction de sa date de naissance
     *
     * @return L'âge de la personne
     */
    @JsonProperty("age")
    public int getAge(){
        return LocalDate.now().getYear() - this.birthYear;
    }


    /**
     * Obtient l'adresse de la personne.
     *
     * @return L'adresse de la personne.
     */
    @JsonProperty("address")
    public String getAddress() {
        return this.address;
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
        return this.hobby;
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
        return this.birthYear;
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
        return this.salary;
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
        return this.prime;
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
            System.out.println(e.getMessage());
            return "{}";
        }
    }

}
