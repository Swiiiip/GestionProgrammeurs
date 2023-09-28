package data;

import utils.Constants;

/**
 * La classe ProgrammeurBean représente un programmeur avec ses attributs tels que
 * le nom, le prénom, l'adresse, le pseudo, le responsable, le hobby, l'année de
 * naissance, le salaire et la prime. Cette classe est utilisée pour stocker et manipuler
 * les données des programmeurs.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class ProgrammeurBean {

    /**
     * Identifiant unique du programmeur.
     */
    private long id;

    /**
     * Nom du programmeur.
     */
    private String lastName;

    /**
     * Prénom du programmeur.
     */
    private String firstName;

    /**
     * Adresse du programmeur.
     */
    private String address;

    /**
     * Pseudo du programmeur.
     */
    private String pseudo;

    /**
     * Responsable du programmeur.
     */
    private String manager;

    /**
     * Hobby du programmeur.
     */
    private String hobby;

    /**
     * Année de naissance du programmeur.
     */
    private int birthYear;

    /**
     * Salaire du programmeur.
     */
    private float salary;

    /**
     * Prime du programmeur.
     */
    private float prime;

    /**
     * Constructeur de la classe ProgrammeurBean pour initialiser un objet ProgrammeurBean
     * avec les informations spécifiées.
     *
     * @param lastName   Le nom du programmeur.
     * @param firstName  Le prénom du programmeur.
     * @param address    L'adresse du programmeur.
     * @param pseudo     Le pseudo du programmeur.
     * @param manager    Le responsable du programmeur.
     * @param hobby      Le hobby du programmeur.
     * @param birthYear  L'année de naissance du programmeur.
     * @param salary     Le salaire du programmeur.
     * @param prime      La prime du programmeur.
     */
    public ProgrammeurBean(String lastName, String firstName, String address, String pseudo,
                           String manager, String hobby, int birthYear, float salary, float prime) {
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

    public ProgrammeurBean() {
        this.lastName = null;
        this.firstName = null;
        this.address = null;
        this.pseudo = null;
        this.manager = null;
        this.hobby = null;
        this.birthYear = 0;
        this.salary = 0;
        this.prime = 0;
    }

    /**
     * Obtient l'identifiant unique du programmeur.
     *
     * @return L'identifiant unique du programmeur.
     */
    public long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du programmeur.
     *
     * @param id L'identifiant unique du programmeur.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Obtient le nom du programmeur.
     *
     * @return Le nom du programmeur.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Définit le nom du programmeur.
     *
     * @param lastName Le nom du programmeur.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Obtient le prénom du programmeur.
     *
     * @return Le prénom du programmeur.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Définit le prénom du programmeur.
     *
     * @param firstName Le prénom du programmeur.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Obtient l'adresse du programmeur.
     *
     * @return L'adresse du programmeur.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Définit l'adresse du programmeur.
     *
     * @param address L'adresse du programmeur.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Obtient le pseudo du programmeur.
     *
     * @return Le pseudo du programmeur.
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Définit le pseudo du programmeur.
     *
     * @param pseudo Le pseudo du programmeur.
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Obtient le responsable du programmeur.
     *
     * @return Le responsable du programmeur.
     */
    public String getManager() {
        return manager;
    }

    /**
     * Définit le responsable du programmeur.
     *
     * @param manager Le responsable du programmeur.
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * Obtient le hobby du programmeur.
     *
     * @return Le hobby du programmeur.
     */
    public String getHobby() {
        return hobby;
    }

    /**
     * Définit le hobby du programmeur.
     *
     * @param hobby Le hobby du programmeur.
     */
    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    /**
     * Obtient l'année de naissance du programmeur.
     *
     * @return L'année de naissance du programmeur.
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Définit l'année de naissance du programmeur.
     *
     * @param birthYear L'année de naissance du programmeur.
     */
    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    /**
     * Obtient le salaire du programmeur.
     *
     * @return Le salaire du programmeur.
     */
    public float getSalary() {
        return salary;
    }

    /**
     * Définit le salaire du programmeur.
     *
     * @param salary Le salaire du programmeur.
     */
    public void setSalary(float salary) {
        this.salary = salary;
    }

    /**
     * Obtient la prime du programmeur.
     *
     * @return La prime du programmeur.
     */
    public float getPrime() {
        return prime;
    }

    /**
     * Définit la prime du programmeur.
     *
     * @param prime La prime du programmeur.
     */
    public void setPrime(float prime) {
        this.prime = prime;
    }

    /**
     * Convertit les informations du programmeur en une chaîne de caractères
     * formatée avec des colonnes alignées, en utilisant les constantes de
     * formatage de la classe Constants.
     *
     * @return Une représentation sous forme de chaîne de caractères des informations du programmeur.
     */
    @Override
    public String toString() {
        String columnFormat = "%-" + Constants.MSGLEN + "." + Constants.MSGLEN + "s";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Constants.ATTRIBUTES.size(); i++) {
            sb.append(String.format(columnFormat, Constants.ATTRIBUTES.get(i)));
            sb.append(" : ").append(Constants.ListAttributes(this).get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
}

