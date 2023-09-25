package data;

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
    List<ProgrammeurBean> getAllProg() throws SQLException;

    /**
     * Récupère un programmeur spécifique à partir de son identifiant.
     *
     * @param Id L'identifiant unique du programmeur à récupérer.
     * @return Un objet ProgrammeurBean représentant le programmeur correspondant à l'Id.
     * @throws SQLException Si une erreur SQL survient lors de l'accès à la base de données.
     */
    ProgrammeurBean getProgById(long Id) throws SQLException;

    /**
     * Supprime un programmeur de la base de données en utilisant son identifiant.
     *
     * @param Id L'identifiant unique du programmeur à supprimer.
     * @throws SQLException Si une erreur SQL survient lors de la suppression dans la base de données.
     */
    void deleteProgById(long Id) throws SQLException;

    /**
     * Ajoute un nouveau programmeur à la base de données.
     *
     * @param programmeur L'objet ProgrammeurBean représentant le nouveau programmeur à ajouter.
     * @throws SQLException Si une erreur SQL survient lors de l'ajout dans la base de données.
     */
    void addProg(ProgrammeurBean programmeur) throws SQLException;

    /**
     * Modifie le salaire d'un programmeur en utilisant son identifiant.
     *
     * @param Id L'identifiant unique du programmeur dont le salaire doit être modifié.
     * @param newSalary Le nouveau salaire à attribuer au programmeur.
     * @throws SQLException Si une erreur SQL survient lors de la mise à jour dans la base de données.
     */
    void setProgSalaryById(long Id, double newSalary) throws SQLException;


    /*---------------------------- MANAGER ----------------------------*/

    ProgrammeurBean getProgWithMaxSalary() throws SQLException;

    ProgrammeurBean getProgWithMinSalary() throws SQLException;

    Map<Integer, Float> getAvgSalaryByAgeProg() throws SQLException;

    int getNbProg() throws SQLException;

    Map<Integer, ProgrammeurBean> getRankProgBySalary() throws SQLException;

    int getCorrelationBetweenAgeAndSalaryProg() throws SQLException;

    List<ManagerBean> getAllManager() throws SQLException;

    ManagerBean getManagerById(long id) throws SQLException;

    ManagerBean getManagerByFullName(String lastName, String firstName) throws SQLException;

    void deleteManagerById(long id) throws SQLException;

    void addManager(ManagerBean manager) throws SQLException;

    void setManagerSalaryById(long id, double newSalary) throws SQLException;

    Map<Float, Integer> getSalaryHistogramManager() throws SQLException;

    /*---------------------------- EXIT ----------------------------*/

    /**
     * Termine l'application et la connexion à la base de données
     */
    void exit();
}
