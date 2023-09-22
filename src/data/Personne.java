package data;

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
     * Obtient l'identifiant unique du personne.
     *
     * @return L'identifiant unique du personne.
     */
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du personne.
     *
     * @param id L'identifiant unique du personne.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtient le nom du personne.
     *
     * @return Le nom du personne.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Définit le nom du personne.
     *
     * @param lastName Le nom du personne.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtient le prénom du personne.
     *
     * @return Le prénom du personne.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Définit le prénom du personne.
     *
     * @param firstName Le prénom du personne.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtient l'adresse du personne.
     *
     * @return L'adresse du personne.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse du personne.
     *
     * @param address L'adresse du personne.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Obtient le hobby du personne.
     *
     * @return Le hobby du personne.
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * Définit le hobby du personne.
     *
     * @param hobby Le hobby du personne.
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * Obtient l'année de naissance du personne.
     *
     * @return L'année de naissance du personne.
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Définit l'année de naissance du personne.
     *
     * @param birthYear L'année de naissance du personne.
     */
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    /**
     * Obtient le salaire du personne.
     *
     * @return Le salaire du personne.
     */
    public float getSalary() {
        return salary;
    }

    /**
     * Définit le salaire du personne.
     *
     * @param salary Le salaire du personne.
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     * Obtient la prime du personne.
     *
     * @return La prime du personne.
     */
    public float getPrime() {
        return prime;
    }

    /**
     * Définit la prime du personne.
     *
     * @param prime La prime du personne.
     */
    public void setPrime(float prime) {
        this.prime = prime;
    }

    @Override
    public String toString() {
        StringBuilder json = new StringBuilder();
        json.append("{\n");
        json.append("\t\"id\": ").append(this.id).append(", ");
        json.append("\t\"firstName\": \"").append(this.firstName).append("\", ");
        json.append("\t\"lastName\": \"").append(this.lastName).append("\", ");
        json.append("\t\"address\": \"").append(this.address).append("\", ");
        json.append("\t\"hobby\": \"").append(this.hobby).append("\", ");
        json.append("\t\"birthYear\": ").append(this.birthYear).append(", ");
        json.append("\t\"salary\": ").append(this.salary).append(", ");
        json.append("\t\"prime\": ").append(this.prime);
        return json.toString();
    }


}
