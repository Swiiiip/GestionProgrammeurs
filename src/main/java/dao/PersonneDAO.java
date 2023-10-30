package dao;

import data.db.Actions;
import personnes.Personne;
import personnes.utils.Address;
import personnes.utils.Coords;
import personnes.utils.Pictures;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Cette classe abstraite définit les méthodes de base pour interagir avec des objets de type {@link Personne}
 * dans une base de données.
 *
 * @param <T> Le type concret de l'objet {@link Personne}.
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public abstract class PersonneDAO<T extends Personne> {

    private final Actions<T> actions = new Actions<>();

    /**
     * Récupère toutes les instances de {@link Personne} depuis la base de données.
     *
     * @return Une liste de toutes les instances de {@link Personne}.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract List<T> getAll() throws SQLException;

    /**
     * Récupère une instance de {@link Personne} par son identifiant unique.
     *
     * @param id L'identifiant de l'objet à récupérer.
     * @return L'instance de {@link Personne} correspondant à l'identifiant donné.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract T getById(int id) throws SQLException;

    /**
     * Récupère une instance de {@link Personne} par son nom complet.
     *
     * @param lastName Le nom de famille de la personne.
     * @param firstName Le prénom de la personne.
     * @return L'instance de {@link Personne} correspondant au nom complet donné.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract T getByFullName(String lastName, String firstName) throws SQLException;

    /**
     * Ajoute une instance de {@link Personne} à la base de données.
     *
     * @param personne L'instance de {@link Personne} à ajouter.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract void add(T personne) throws SQLException;

    /**
     * Modifie le salaire d'une personne par son identifiant unique.
     *
     * @param id L'identifiant de la personne.
     * @param newSalary Le nouveau salaire de la personne.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract void setSalaryById(int id, float newSalary) throws SQLException;

    /**
     * Récupère le nombre total d'instances de {@link Personne} dans la base de données.
     *
     * @return Le nombre total d'instances de {@link Personne}.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract int getCount() throws SQLException;

    /**
     * Récupère l'instance de {@link Personne} avec le salaire le plus élevé.
     *
     * @return L'instance de {@link Personne} avec le salaire le plus élevé.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract T getWithMaxSalary() throws SQLException;

    /**
     * Récupère l'instance de {@link Personne} avec le salaire le plus bas.
     *
     * @return L'instance de {@link Personne} avec le salaire le plus bas.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract T getWithMinSalary() throws SQLException;

    /**
     * Récupère la moyenne des salaires par âge sous forme de {@link Map}.
     *
     * @return Une {@link Map} associant l'âge des personnes à leur salaire moyen.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract Map<Integer, Float> getAvgSalaryByAge() throws SQLException;

    /**
     * Récupère un classement des personnes par salaire sous forme de {@link Map}.
     *
     * @return Une {@link Map} associant le salaire des personnes à leur instance de {@link Personne}.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract Map<Integer, T> getRankBySalary() throws SQLException;

    /**
     * Récupère un histogramme des salaires sous forme de {@link Map}.
     *
     * @return Une {@link Map} associant les salaires aux nombres de personnes ayant ce salaire.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract Map<Float, Integer> getSalaryHistogram() throws SQLException;

    /**
     * Récupère la moyenne des salaires par genre sous forme de {@link Map}.
     *
     * @return Une {@link Map} associant le genre des personnes à leur salaire moyen.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract Map<String, Float> getAverageSalaryByGender() throws SQLException;

    /**
     * Supprime une instance de {@link Personne} par son identifiant unique.
     *
     * @param id L'identifiant de la personne à supprimer.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract void deleteById(int id) throws SQLException;

    /**
     * Supprime toutes les instances de {@link Personne} de la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract void deleteAll() throws SQLException;

    /**
     * Réinitialise l'index des personnes dans la base de données.
     *
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     */
    public abstract void resetIndex() throws SQLException;

    /**
     * Récupère l'étiquette du type géré par ce DAO.
     *
     * @return L'étiquette du type géré par le DAO.
     */
    public abstract String getTypeLabel();

    /**
     * Supprime les données inutiles associées à ce DAO.
     *
     * @throws SQLException En cas d'erreur lors de la suppression des données inutiles.
     */
    public void deleteUtils() throws SQLException {
        actions.deleteUtils();
    }

    /**
     * Ajoute des images ({@link Pictures}) à la base de données.
     *
     * @param pictures Les images à ajouter.
     * @throws SQLException En cas d'erreur lors de l'ajout des images.
     */
    public void addPictures(Pictures pictures) throws SQLException {
        actions.addPictures(pictures);
    }

    /**
     * Ajoute des coordonnées ({@link Coords}) à la base de données.
     *
     * @param coords Les coordonnées à ajouter.
     * @throws SQLException En cas d'erreur lors de l'ajout des coordonnées.
     */
    public void addCoords(Coords coords) throws SQLException {
        actions.addCoords(coords);
    }

    /**
     * Ajoute une adresse ({@link Address}) à la base de données.
     *
     * @param address L'adresse à ajouter.
     * @throws SQLException En cas d'erreur lors de l'ajout de l'adresse.
     */
    public void addAddress(Address address) throws SQLException{
        actions.addAddress(address);
    }

    /**
     * Récupère des images ({@link Pictures}) à partir de la base de données.
     *
     * @param pictures Les images à récupérer.
     * @return Les images récupérées.
     * @throws SQLException En cas d'erreur lors de la récupération des images.
     */
    public Pictures getPictures(Pictures pictures) throws SQLException {
        return actions.getFullPictures(pictures);
    }

    /**
     * Récupère des coordonnées ({@link Coords}) à partir de la base de données.
     *
     * @param coords Les coordonnées à récupérer.
     * @return Les coordonnées récupérées.
     * @throws SQLException En cas d'erreur lors de la récupération des coordonnées.
     */
    public Coords getCoords(Coords coords) throws SQLException {
        return actions.getFullCoords(coords);
    }

    /**
     * Récupère une adresse ({@link Address}) à partir de la base de données.
     *
     * @param address L'adresse à récupérer.
     * @return L'adresse récupérée.
     * @throws SQLException En cas d'erreur lors de la récupération de l'adresse.
     */
    public Address getAddress(Address address) throws SQLException{
        return actions.getFullAddress(address);
    }

    /**
     * Récupère des images ({@link Pictures}) par leur identifiant unique.
     *
     * @param id L'identifiant des images à récupérer.
     * @return Les images récupérées.
     * @throws SQLException En cas d'erreur lors de la récupération des images.
     */
    public Pictures getPicturesById(int id) throws SQLException {
        return actions.getPicturesById(id);
    }

    /**
     * Exécute les opérations de nettoyage et de fermeture associées à cette instance de {@link PersonneDAO}.
     */
    public void exit() {
        actions.exit();
    }
}

