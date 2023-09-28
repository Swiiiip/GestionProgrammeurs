package personnes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * La classe ProgrammeurBean représente un programmeur avec ses attributs tels que
 * le nom, le prénom, l'adresse, le pseudo, le responsable, le hobby, l'année de
 * naissance, le salaire et la prime. Cette classe est utilisée pour stocker et manipuler
 * les données des programmeurs.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProgrammeurBean extends Personne{

    /**
     * Pseudo du programmeur.
     */
    private String pseudo;

    /**
     * Responsable du programmeur.
     */
    private ManagerBean manager;


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
                           ManagerBean manager, String hobby, int birthYear, float salary, float prime) {
        super(lastName,firstName,address,hobby,birthYear,salary,prime);
        this.pseudo = pseudo;
        this.manager = manager;
    }

    public ProgrammeurBean() {
        super();
        this.pseudo = null;
        this.manager = null;
    }
    /**
     * Obtient le pseudo du programmeur.
     *
     * @return Le pseudo du programmeur.
     */
    @JsonProperty("pseudo")
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
    @JsonProperty("manager")
    public ManagerBean getManager() {
        return manager;
    }

    /**
     * Définit le responsable du programmeur.
     *
     * @param manager Le responsable du programmeur.
     */
    public void setManager(ManagerBean manager) {
        this.manager = manager;
    }

}

