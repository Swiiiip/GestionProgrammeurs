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

/**
 * Cette classe représente un type de personne, le manager avec des informations personnelles telles que le nom, le prénom,
 * le genre, la date de naissance, l'adresse, les coordonnées géographiques, les photos, les loisirs,
 * le salaire, la prime et le département auquel le manager est affilié.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Manager extends Personne {

    /**
     * Le département auquel le manager est affilié.
     */
    private Departments department;

    /**
     * Constructeur par défaut de la classe Manager. Initialise tous les attributs à des valeurs par défaut.
     */
    public Manager() {
        super();
        this.department = null;
    }

    /**
     * Constructeur paramétré de la classe Manager. Initialise les attributs du manager avec les valeurs spécifiées.
     *
     * @param title     Le titre du manager.
     * @param lastName  Le nom de famille du manager.
     * @param firstName Le prénom du manager.
     * @param gender    Le genre du manager.
     * @param pictures  Les photos du manager.
     * @param address   L'adresse du manager.
     * @param coords    Les coordonnées géographiques du manager.
     * @param hobby     Les loisirs du manager.
     * @param birthYear L'année de naissance du manager.
     * @param salary    Le salaire du manager.
     * @param prime     La prime du manager.
     * @param department Le département auquel le manager est affilié.
     */
    public Manager(Title title, String lastName, String firstName, Gender gender, Pictures pictures, Address address, Coords coords, Hobbies hobby, int birthYear, float salary, float prime, Departments department) {
        super(title, lastName, firstName, gender, pictures, address, coords, hobby, birthYear, salary, prime);
        this.department = department;
    }

    /**
     * Obtient le département auquel le manager est affilié.
     *
     * @return Le département du manager.
     */
    @JsonProperty("department")
    public String getDepartment() {
        return department.getDepartment();
    }

    /**
     * Définit le département auquel le manager est affilié.
     *
     * @param department Le département du manager.
     */
    public void setDepartment(Departments department) {
        this.department = department;
    }

    /**
     * Obtient un mapping de noms de colonnes et de valeurs pour ce manager, y compris les attributs hérités de Personne.
     *
     * @return Un mapping de noms de colonnes et de valeurs associées à ce manager.
     */
    @Override
    public LinkedHashMap<String, Object> getColumns() {
        LinkedHashMap<String, Object> columns = super.getColumns();
        columns.put("Department", this.department.getDepartment());
        return columns;
    }
}

