package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

/**
 * Cette classe représente une adresse physique. Elle peut être utilisée pour stocker des informations sur l'emplacement d'une personne.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
@JsonPropertyOrder({"streetNumber", "streetName", "city", "state", "country", "postcode"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Address {

    /**
     * L'identifiant unique de l'adresse.
     */
    private int id;

    /**
     * Le numéro de rue de l'adresse.
     */
    private int streetNumber;

    /**
     * Le nom de la rue de l'adresse.
     */
    private String streetName;

    /**
     * La ville de l'adresse.
     */
    private String city;

    /**
     * L'État ou la province de l'adresse.
     */
    private String state;

    /**
     * Le pays de l'adresse.
     */
    private String country;

    /**
     * Le code postal de l'adresse.
     */
    private String postcode;

    /**
     * Constructeur par défaut de la classe Address. Initialise les champs à des valeurs nulles ou par défaut.
     */
    public Address() {
        this.streetNumber = 0;
        this.streetName = null;
        this.city = null;
        this.state = null;
        this.country = null;
        this.postcode = null;
    }

    /**
     * Constructeur de la classe `Address` avec des paramètres pour initialiser tous les champs.
     *
     * @param streetNumber Le numéro de rue de l'adresse.
     * @param streetName Le nom de la rue de l'adresse.
     * @param city La ville de l'adresse.
     * @param state L'État ou la province de l'adresse.
     * @param country Le pays de l'adresse.
     * @param postcode Le code postal de l'adresse.
     */
    public Address(int streetNumber, String streetName, String city, String state, String country, String postcode) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postcode = postcode;
    }

    /**
     * Obtient l'identifiant unique de l'adresse.
     *
     * @return L'identifiant unique de l'adresse.
     */
    @JsonIgnore
    public int getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de l'adresse.
     *
     * @param id L'identifiant unique de l'adresse.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtient le numéro de rue de l'adresse.
     *
     * @return Le numéro de rue de l'adresse.
     */
    @JsonProperty("streetNumber")
    public int getStreetNumber() {
        return this.streetNumber;
    }

    /**
     * Définit le numéro de rue de l'adresse.
     *
     * @param streetNumber Le numéro de rue de l'adresse.
     */
    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    /**
     * Obtient le nom de la rue de l'adresse.
     *
     * @return Le nom de la rue de l'adresse.
     */
    @JsonProperty("streetName")
    public String getStreetName() {
        return this.streetName;
    }

    /**
     * Définit le nom de la rue de l'adresse.
     *
     * @param streetName Le nom de la rue de l'adresse.
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Obtient la ville de l'adresse.
     *
     * @return La ville de l'adresse.
     */
    @JsonProperty("city")
    public String getCity() {
        return this.city;
    }

    /**
     * Définit la ville de l'adresse.
     *
     * @param city La ville de l'adresse.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Obtient l'État ou la province de l'adresse.
     *
     * @return L'État ou la province de l'adresse.
     */
    @JsonProperty("state")
    public String getState() {
        return this.state;
    }

    /**
     * Définit l'État ou la province de l'adresse.
     *
     * @param state L'État ou la province de l'adresse.
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Obtient le pays de l'adresse.
     *
     * @return Le pays de l'adresse.
     */
    @JsonProperty("country")
    public String getCountry() {
        return this.country;
    }

    /**
     * Définit le pays de l'adresse.
     *
     * @param country Le pays de l'adresse.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Obtient le code postal de l'adresse.
     *
     * @return Le code postal de l'adresse.
     */
    @JsonProperty("postcode")
    public String getPostcode() {
        return postcode;
    }

    /**
     * Définit le code postal de l'adresse.
     *
     * @param postcode Le code postal de l'adresse.
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * Compare cet objet Address avec un autre objet pour déterminer s'ils sont égaux.
     *
     * @param o L'objet à comparer avec cet objet Address.
     * @return true si les objets sont égaux en termes de numéro de rue, de nom de rue, de ville, d'État,
     * de pays et de code postale, false sinon.
     */
    @Override
    public boolean equals(Object o) {
        if (this.getClass() != o.getClass())
            return false;

        return this.streetNumber == ((Address) o).getStreetNumber()
                && this.streetName.equals(((Address) o).getStreetName())
                && this.city.equals(((Address) o).getCity())
                && this.state.equals(((Address) o).getState())
                && this.country.equals(((Address) o).getCountry())
                && this.postcode.equals(((Address) o).getPostcode());
    }


    /**
     * Fournit une représentation textuelle de l'adresse, incluant tous les détails.
     *
     * @return Une chaîne de caractères représentant l'adresse complète.
     */
    @Override
    public String toString() {
        return this.streetNumber + " " + this.streetName + ", " + this.city +
                ", " + this.state + ", " + this.country + ", " + this.postcode;
    }
}
