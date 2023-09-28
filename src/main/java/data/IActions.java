package data;

import personnes.Manager;
import personnes.Programmeur;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * L'interface ActionsBDD définit un ensemble de méthodes pour effectuer des
 * opérations liées à une base de données de programmeurs.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public interface IActions {

    /*---------------------------- PROGRAMMEUR ----------------------------*/

    /**
     * Récupère la liste de tous les programmeurs présents dans la base de données.
     *
     * @return Une liste contenant tous les objets ProgrammeurBean de la base de données.
     * @throws SQLException Si une erreur SQL survient lors de l'accès à la base de données.
     */
    List<Programmeur> getAllProg() throws SQLException;

    /**
     * Récupère un programmeur spécifique à partir de son identifiant.
     *
     * @param id L'identifiant unique du programmeur à récupérer.
     * @return Un objet ProgrammeurBean représentant le programmeur correspondant à l'Id.
     * @throws SQLException Si une erreur SQL survient lors de l'accès à la base de données.
     */
    Programmeur getProgById(int id) throws SQLException;

    /**
     * Supprime un programmeur de la base de données en utilisant son identifiant.
     *
     * @param id L'identifiant unique du programmeur à supprimer.
     * @throws SQLException Si une erreur SQL survient lors de la suppression dans la base de données.
     */
    void deleteProgById(int id) throws SQLException;

    /**
     * Ajoute un nouveau programmeur à la base de données.
     *
     * @param programmeur L'objet ProgrammeurBean représentant le nouveau programmeur à ajouter.
     * @throws SQLException Si une erreur SQL survient lors de l'ajout dans la base de données.
     */
    void addProg(Programmeur programmeur) throws SQLException;

    /**
     * Modifie le salaire d'un programmeur en utilisant son identifiant.
     *
     * @param id L'identifiant unique du programmeur dont le salaire doit être modifié.
     * @param newSalary Le nouveau salaire à attribuer au programmeur.
     * @throws SQLException Si une erreur SQL survient lors de la mise à jour dans la base de données.
     */
    void setProgSalaryById(int id, float newSalary) throws SQLException;


    /*---------------------------- MANAGER ----------------------------*/

    Programmeur getProgWithMaxSalary() throws SQLException;

    Programmeur getProgWithMinSalary() throws SQLException;

    Map<Integer, Float> getAvgSalaryByAgeProg() throws SQLException;

    int getNbProg() throws SQLException;

    Map<Integer, Programmeur> getRankProgBySalary() throws SQLException;

    double getCorrelationBetweenAgeAndSalaryProg() throws SQLException;

    List<Manager> getAllManager() throws SQLException;

    Manager getManagerById(int id) throws SQLException;

    Manager getManagerByFullName(String lastName, String firstName) throws SQLException;

    void deleteManagerById(int id) throws SQLException;

    void addManager(Manager manager) throws SQLException;

    void setManagerSalaryById(int id, float newSalary) throws SQLException;

    Map<Float, Integer> getSalaryHistogramManager() throws SQLException;

    /*---------------------------- EXIT ----------------------------*/

    /**
     * Termine l'application et la connexion à la base de données
     */
    void exit();

    /*---------------------------- RESET ----------------------------*/

    void deleteALLProgs() throws SQLException;

    void deleteALLManagers() throws SQLException;

    void resetIndexProg() throws SQLException;

    void resetIndexManager() throws SQLException;
}
