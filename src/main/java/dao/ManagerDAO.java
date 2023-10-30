package dao;

import data.db.Actions;
import personnes.Manager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Classe spécifique de DAO pour la manipulation des entités de type Manager.
 * Cette classe hérite des fonctionnalités de la classe abstraite PersonneDAO et implémente les méthodes
 * spécifiques pour les Managers.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class ManagerDAO extends PersonneDAO<Manager> {
    private final Actions<Manager> actions = new Actions<>();

    /**
     * Récupère la liste de tous les Managers depuis la base de données.
     *
     * @return Une liste de tous les Managers.
     * @throws SQLException En cas d'erreur lors de la récupération des données depuis la base de données.
     */
    @Override
    public List<Manager> getAll() throws SQLException {
        return actions.getAll("Manager");
    }

    /**
     * Récupère un Manager en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique du Manager à récupérer.
     * @return Le Manager correspondant à l'identifiant spécifié.
     * @throws SQLException En cas d'erreur lors de la récupération des données depuis la base de données.
     */
    @Override
    public Manager getById(int id) throws SQLException {
        return actions.getById("Manager", id);
    }

    /**
     * Récupère un Manager en fonction de son nom complet (nom de famille et prénom).
     *
     * @param lastName  Le nom de famille du Manager.
     * @param firstName Le prénom du Manager.
     * @return Le Manager correspondant au nom complet spécifié.
     * @throws SQLException En cas d'erreur lors de la récupération des données depuis la base de données.
     */
    @Override
    public Manager getByFullName(String lastName, String firstName) throws SQLException {
        return actions.getByFullName("Manager", lastName, firstName);
    }

    /**
     * Ajoute un nouveau Manager à la base de données.
     *
     * @param manager Le Manager a ajouter.
     * @throws SQLException En cas d'erreur lors de l'ajout du Manager à la base de données.
     */
    @Override
    public void add(Manager manager) throws SQLException {
        actions.addPersonne(manager);
    }

    /**
     * Met à jour le salaire d'un Manager en fonction de son identifiant unique.
     *
     * @param id        L'identifiant unique du Manager à mettre à jour.
     * @param newSalary Le nouveau salaire à attribuer au Manager.
     * @throws SQLException En cas d'erreur lors de la mise à jour du salaire dans la base de données.
     */
    @Override
    public void setSalaryById(int id, float newSalary) throws SQLException {
        actions.setSalaryById("Manager", id, newSalary);
    }

    /**
     * Récupère le nombre total de Managers dans la base de données.
     *
     * @return Le nombre total de Managers dans la base de données.
     * @throws SQLException En cas d'erreur lors de la récupération du nombre de Managers.
     */
    @Override
    public int getCount() throws SQLException {
        return actions.getCount("Manager");
    }

    /**
     * Récupère le Manager avec le salaire le plus élevé dans la base de données.
     *
     * @return Le Manager avec le salaire le plus élevé.
     * @throws SQLException En cas d'erreur lors de la récupération du Manager avec le salaire le plus élevé.
     */
    @Override
    public Manager getWithMaxSalary() throws SQLException {
        return actions.getWithAggregatedSalary("Manager", "MAX");
    }

    /**
     * Récupère le Manager avec le salaire le plus bas dans la base de données.
     *
     * @return Le Manager avec le salaire le plus bas.
     * @throws SQLException En cas d'erreur lors de la récupération du Manager avec le salaire le plus bas.
     */
    @Override
    public Manager getWithMinSalary() throws SQLException {
        return actions.getWithAggregatedSalary("Manager", "MIN");
    }

    /**
     * Récupère la moyenne des salaires par tranche d'âge pour les Managers.
     *
     * @return Un mapping des tranches d'âge aux moyennes de salaire correspondantes.
     * @throws SQLException En cas d'erreur lors du calcul des moyennes de salaire par tranche d'âge.
     */
    @Override
    public Map<Integer, Float> getAvgSalaryByAge() throws SQLException {
        return actions.getAvgSalaryByAge("Manager");
    }

    /**
     * Récupère un classement des Managers par salaire, du plus élevé au plus bas.
     *
     * @return Un mapping des identifiants uniques aux Managers, classés par salaire.
     * @throws SQLException En cas d'erreur lors du classement des Managers par salaire.
     */
    @Override
    public Map<Integer, Manager> getRankBySalary() throws SQLException {
        return actions.getRankBySalary("Manager");
    }

    /**
     * Récupère un histogramme des salaires pour les Managers, indiquant le nombre de Managers avec des salaires spécifiques.
     *
     * @return Un mapping des salaires aux nombres de Managers correspondants.
     * @throws SQLException En cas d'erreur lors de la création de l'histogramme des salaires.
     */
    @Override
    public Map<Float, Integer> getSalaryHistogram() throws SQLException {
        return actions.getSalaryHistogram("Manager");
    }

    /**
     * Récupère la moyenne des salaires par genre pour les Managers.
     *
     * @return Un mapping des genres aux moyennes de salaire correspondantes.
     * @throws SQLException En cas d'erreur lors du calcul des moyennes de salaire par genre.
     */
    @Override
    public Map<String, Float> getAverageSalaryByGender() throws SQLException {
        return actions.getAverageSalaryByGender("Manager");
    }

    /**
     * Supprime un Manager en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique du Manager à supprimer.
     * @throws SQLException En cas d'erreur lors de la suppression du Manager de la base de données.
     */
    @Override
    public void deleteById(int id) throws SQLException {
        actions.deleteById("Manager", id);
    }

    /**
     * Supprime tous les Managers de la base de données.
     *
     * @throws SQLException En cas d'erreur lors de la suppression de tous les Managers.
     */
    @Override
    public void deleteAll() throws SQLException {
        actions.deleteAll("Manager");
    }

    /**
     * Réinitialise l'index des Managers dans la base de données.
     *
     * @throws SQLException En cas d'erreur lors de la réinitialisation de l'index.
     */
    @Override
    public void resetIndex() throws SQLException {
        actions.resetIndex("Manager");
    }

    /**
     * Obtient le libellé du type d'entité manipulée par ce DAO.
     *
     * @return Le libellé du type d'entité (dans ce cas, "manager").
     */
    @Override
    public String getTypeLabel() {
        return "manager";
    }
}
