package personnes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import personnes.utils.Coords;
import personnes.utils.Pictures;
import utils.Gender;
import utils.Hobbies;

import java.util.LinkedHashMap;

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
public class Programmeur extends Personne {

    /**
     * Pseudo du programmeur.
     */
    private String pseudo;

    /**
     * Responsable du programmeur.
     */
    private Manager manager;


    /**
     * Constructeur de la classe ProgrammeurBean pour initialiser un objet ProgrammeurBean
     * avec les informations spécifiées.
     *
     * @param lastName  Le nom du programmeur.
     * @param firstName Le prénom du programmeur.
     * @param gender    Le genre du programmeur
     * @param address   L'adresse du programmeur.
     * @param pseudo    Le pseudo du programmeur.
     * @param manager   Le responsable du programmeur.
     * @param hobby     Le hobby du programmeur.
     * @param birthYear L'année de naissance du programmeur.
     * @param salary    Le salaire du programmeur.
     * @param prime     La prime du programmeur.
     */
    public Programmeur(String title, String lastName, String firstName, Gender gender, Pictures pictures, String address, Coords coords, String pseudo,
                       Manager manager, Hobbies hobby, int birthYear, float salary, float prime) {
        super(title, lastName, firstName, gender, pictures, address, coords, hobby, birthYear, salary, prime);
        this.pseudo = pseudo;
        this.manager = manager;
    }

    public Programmeur() {
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
    public Manager getManager() {
        return manager;
    }

    /**
     * Définit le responsable du programmeur.
     *
     * @param manager Le responsable du programmeur.
     */
    public void setManager(Manager manager) {
        this.manager = manager;
    }

    @Override
    public LinkedHashMap<String, Object> getColumns() {
        LinkedHashMap<String, Object> columns = super.getColumns();
        columns.put("Pseudo", this.pseudo);
        columns.put("Id_manager", this.manager.getId());
        return columns;
    }

}