package dao;

import data.db.Actions;
import personnes.Programmeur;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Classe spécifique de DAO pour la manipulation des entités de type Programmeur.
 * Cette classe hérite des fonctionnalités de la classe abstraite PersonneDAO et implémente les méthodes
 * spécifiques pour les Programmeurs.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class ProgrammeurDAO extends PersonneDAO<Programmeur> {
    private final Actions<Programmeur> actions = new Actions<>();

    /**
     * Récupère la liste de tous les Programmeurs depuis la base de données.
     *
     * @return Une liste de tous les Programmeurs.
     * @throws SQLException En cas d'erreur lors de la récupération des données depuis la base de données.
     */
    @Override
    public List<Programmeur> getAll() throws SQLException {
        return actions.getAll("Programmeur");
    }

    /**
     * Récupère un Programmeur en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique du Programmeur à récupérer.
     * @return Le Programmeur correspondant à l'identifiant spécifié.
     * @throws SQLException En cas d'erreur lors de la récupération des données depuis la base de données.
     */
    @Override
    public Programmeur getById(int id) throws SQLException {
        return actions.getById("Programmeur", id);
    }

    /**
     * Récupère un Programmeur en fonction de son nom complet (nom de famille et prénom).
     *
     * @param lastName  Le nom de famille du Programmeur.
     * @param firstName Le prénom du Programmeur.
     * @return Le Programmeur correspondant au nom complet spécifié.
     * @throws SQLException En cas d'erreur lors de la récupération des données depuis la base de données.
     */
    @Override
    public Programmeur getByFullName(String lastName, String firstName) throws SQLException {
        return actions.getByFullName("Programmeur", lastName, firstName);
    }

    /**
     * Ajoute un nouveau Programmeur à la base de données.
     *
     * @param prog Le Programmeur à ajouter.
     * @throws SQLException En cas d'erreur lors de l'ajout du Programmeur à la base de données.
     */
    @Override
    public void add(Programmeur prog) throws SQLException {
        actions.addPersonne(prog);
    }

    /**
     * Met à jour le salaire d'un Programmeur en fonction de son identifiant unique.
     *
     * @param id        L'identifiant unique du Programmeur à mettre à jour.
     * @param newSalary Le nouveau salaire à attribuer au Programmeur.
     * @throws SQLException En cas d'erreur lors de la mise à jour du salaire dans la base de données.
     */
    @Override
    public void setSalaryById(int id, float newSalary) throws SQLException {
        actions.setSalaryById("Programmeur", id, newSalary);
    }

    /**
     * Récupère le nombre total de Programmeurs dans la base de données.
     *
     * @return Le nombre total de Programmeurs dans la base de données.
     * @throws SQLException En cas d'erreur lors de la récupération du nombre de Programmeurs.
     */
    @Override
    public int getCount() throws SQLException {
        return actions.getCount("Programmeur");
    }

    /**
     * Récupère le Programmeur avec le salaire le plus élevé dans la base de données.
     *
     * @return Le Programmeur avec le salaire le plus élevé.
     * @throws SQLException En cas d'erreur lors de la récupération du Programmeur avec le salaire le plus élevé.
     */
    @Override
    public Programmeur getWithMaxSalary() throws SQLException {
        return actions.getWithAggregatedSalary("Programmeur", "MAX");
    }

    /**
     * Récupère le Programmeur avec le salaire le plus bas dans la base de données.
     *
     * @return Le Programmeur avec le salaire le plus bas.
     * @throws SQLException En cas d'erreur lors de la récupération du Programmeur avec le salaire le plus bas.
     */
    @Override
    public Programmeur getWithMinSalary() throws SQLException {
        return actions.getWithAggregatedSalary("Programmeur", "MIN");
    }

    /**
     * Récupère la moyenne des salaires par tranche d'âge pour les Programmeurs.
     *
     * @return Un mapping des tranches d'âge aux moyennes de salaire correspondantes.
     * @throws SQLException En cas d'erreur lors du calcul des moyennes de salaire par tranche d'âge.
     */
    @Override
    public Map<Integer, Float> getAvgSalaryByAge() throws SQLException {
        return actions.getAvgSalaryByAge("Programmeur");
    }

    /**
     * Récupère un classement des Programmeurs par salaire, du plus élevé au plus bas.
     *
     * @return Un mapping des identifiants uniques aux Programmeurs, classés par salaire.
     * @throws SQLException En cas d'erreur lors du classement des Programmeurs par salaire.
     */
    @Override
    public Map<Integer, Programmeur> getRankBySalary() throws SQLException {
        return actions.getRankBySalary("Programmeur");
    }

    /**
     * Récupère un histogramme des salaires pour les Programmeurs, indiquant le nombre de Programmeurs avec des salaires spécifiques.
     *
     * @return Un mapping des salaires aux nombres de Programmeurs correspondants.
     * @throws SQLException En cas d'erreur lors de la création de l'histogramme des salaires.
     */
    @Override
    public Map<Float, Integer> getSalaryHistogram() throws SQLException {
        return actions.getSalaryHistogram("Programmeur");
    }

    /**
     * Récupère la moyenne des salaires par genre pour les Programmeurs.
     *
     * @return Un mapping des genres aux moyennes de salaire correspondantes.
     * @throws SQLException En cas d'erreur lors du calcul des moyennes de salaire par genre.
     */
    @Override
    public Map<String, Float> getAverageSalaryByGender() throws SQLException {
        return actions.getAverageSalaryByGender("Programmeur");
    }

    /**
     * Supprime un Programmeur en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique du Programmeur à supprimer.
     * @throws SQLException En cas d'erreur lors de la suppression du Programmeur de la base de données.
     */
    @Override
    public void deleteById(int id) throws SQLException {
        actions.deleteById("Programmeur", id);
    }

    /**
     * Supprime tous les Programmeurs de la base de données.
     *
     * @throws SQLException En cas d'erreur lors de la suppression de tous les Programmeurs.
     */
    @Override
    public void deleteAll() throws SQLException {
        actions.deleteAll("Programmeur");
    }

    /**
     * Réinitialise l'index des Programmeurs dans la base de données.
     *
     * @throws SQLException En cas d'erreur lors de la réinitialisation de l'index.
     */
    @Override
    public void resetIndex() throws SQLException {
        actions.resetIndex("Programmeur");
    }

    /**
     * Obtient le libellé du type d'entité manipulée par ce DAO.
     *
     * @return Le libellé du type d'entité (dans ce cas, "programmeur").
     */
    @Override
    public String getTypeLabel() {
        return "programmeur";
    }
}