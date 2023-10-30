package data.generator;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;
import data.api.mapping.MapManagerFromAPI;
import data.api.mapping.MapProgrammeurFromAPI;
import personnes.Manager;
import personnes.Programmeur;
import personnes.utils.Pictures;

import java.sql.SQLException;

/**
 * Cette classe est utilisée pour générer des données de Programmeurs et Managers à partir de l'API
 * et les stocker dans une base de données SQL.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class DataGenerator {

    /**
     * Le nombre de Programmeurs à générer.
     */
    private final int NBPROGS;

    /**
     * Le nombre de Managers à générer.
     */
    private final int NBMANAGERS;

    /**
     * DAO pour gérer les données des Programmeurs dans la base de données SQL.
     */
    private final ProgrammeurDAO programmeurDAO = new ProgrammeurDAO();

    /**
     * DAO pour gérer les données des Managers dans la base de données SQL.
     */
    private final ManagerDAO managerDAO = new ManagerDAO();

    /**
     * Constructeur de la classe DataGenerator.
     *
     * @param nbProgs Le nombre de Programmeurs à générer.
     * @param nbManagers Le nombre de Managers à générer.
     */
    public DataGenerator(int nbProgs, int nbManagers) {
        this.NBPROGS = nbProgs;
        this.NBMANAGERS = nbManagers;
        this.init();
        programmeurDAO.exit();
        managerDAO.exit();
    }

    /**
     * Initialise le générateur de données en supprimant les données existantes dans la base de données,
     * en réinitialisant les index, en ajoutant des images par défaut, en ajoutant des Managers et en ajoutant des Programmeurs.
     */
    private void init() {
        this.delete();
        this.resetIndex();
        this.addDefaultPictures();
        this.addManager();
        this.addProgrammeur();
    }

    /**
     * Supprime toutes les données de Programmeurs et Managers de la base de données.
     */
    private void delete() {
        try {
            programmeurDAO.deleteAll();
            managerDAO.deleteAll();
            programmeurDAO.deleteUtils();
        } catch (SQLException e) {
            System.err.println("La suppression de toutes les données dans la base de données a échoué. " + e.getMessage());
            throw new SecurityException();
        }
    }

    /**
     * Réinitialise les index des données de Programmeurs et Managers dans la base de données.
     */
    private void resetIndex() {
        try {
            programmeurDAO.resetIndex();
            managerDAO.resetIndex();
        } catch (SQLException e) {
            System.err.println("La réinitialisation des index dans la base de données a échoué. " + e.getMessage());
            throw new SecurityException();
        }
    }

    /**
     * Ajoute des images par défaut pour les Personnes dans la base de données.
     */
    private void addDefaultPictures() {
        Pictures defaultFemale = new Pictures("https://www.w3schools.com/howto/img_avatar2.png", "", "");
        Pictures defaultMale = new Pictures("https://www.w3schools.com/howto/img_avatar.png", "", "");
        try {
            programmeurDAO.addPictures(defaultMale);
            programmeurDAO.addPictures(defaultFemale);
        } catch (SQLException e) {
            System.err.println("L'ajout des images par défaut dans la base de données a échoué. " + e.getMessage());
            throw new SecurityException();
        }
    }

    /**
     * Ajoute des Managers à partir de l'API dans la base de données.
     */
    private void addManager() {
        for (int i = 0; i < NBMANAGERS; i++) {
            Manager manager;
            try {
                manager = new MapManagerFromAPI(managerDAO).map();
            } catch (Exception e) {
                System.err.println("La récupération des données pour le manager " + (i + 1) + " a échoué. " + e.getMessage());
                throw new SecurityException();
            }
            try {
                managerDAO.add(manager);
                System.out.println("Ajout du manager id " + (i + 1) + " dans la base de données.");
            } catch (SQLException e) {
                System.err.println("L'ajout du manager " + (i + 1) + " dans la base de données a échoué. " + e.getMessage());
                throw new SecurityException();
            }
        }
    }

    /**
     * Ajoute des Programmeurs à partir de l'API dans la base de données.
     */
    private void addProgrammeur() {
        for (int i = 0; i < NBPROGS; i++) {
            Programmeur prog;
            try {
                prog = new MapProgrammeurFromAPI(NBMANAGERS, programmeurDAO, managerDAO).map();
            } catch (Exception e) {
                System.err.println("La récupération des données pour le programmeur " + (i + 1) + " a échoué. " + e.getMessage());
                throw new SecurityException();
            }
            try {
                programmeurDAO.add(prog);
                System.out.println("Ajout du programmeur id " + (i + 1) + " dans la base de données.");
            } catch (SQLException e) {
                System.err.println("L'ajout du programmeur " + (i + 1) + " dans la base de données a échoué. " + e.getMessage());
                throw new SecurityException();
            }
        }
    }
}
