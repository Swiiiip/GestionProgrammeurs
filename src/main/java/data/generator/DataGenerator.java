package data.generator;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;
import data.api.mapping.MapManagerFromAPI;
import data.api.mapping.MapProgrammeurFromAPI;
import personnes.Manager;
import personnes.Programmeur;
import personnes.utils.Coords;
import personnes.utils.Pictures;

import java.sql.SQLException;

public class DataGenerator {

    private final int NBPROGS;
    private final int NBMANAGERS;

    private final ProgrammeurDAO programmeurDAO = new ProgrammeurDAO();
    private final ManagerDAO managerDAO = new ManagerDAO();

    public DataGenerator(int nbProgs, int nbManagers) {
        this.NBPROGS = nbProgs;
        this.NBMANAGERS = nbManagers;
        loadData();
        programmeurDAO.exit();
        managerDAO.exit();
    }

    private void loadData() {
        try {
            programmeurDAO.deleteAll();
            managerDAO.deleteAll();
            programmeurDAO.deleteUtils();
        } catch (SQLException e) {
            System.err.println("La suppression de toutes les données a échouée. " + e.getMessage());
            throw new SecurityException();
        }

        try {
            programmeurDAO.resetIndex();
            managerDAO.resetIndex();
        } catch (SQLException e) {
            System.err.println("La réinitialisation des index à échouée. " + e.getMessage());
            throw new SecurityException();
        }
        Pictures defaultFemale = new Pictures("https://www.w3schools.com/howto/img_avatar2.png", "", "");
        Pictures defaultMale = new Pictures("https://www.w3schools.com/howto/img_avatar.png", "", "");
        try {
            programmeurDAO.addPictures(defaultMale);
            programmeurDAO.addPictures(defaultFemale);
        } catch (SQLException e) {
            System.err.println("L'ajout des images par défaut a échouée. " + e.getMessage());
            throw new SecurityException();
        }

        for (int i = 0; i < NBMANAGERS; i++) {
            Manager manager;
            try {
                manager = new MapManagerFromAPI(managerDAO).map();
            } catch (Exception e) {
                System.err.println("La récupération des données pour le manager " + (i + 1) + " a échouée. " + e.getMessage());
                throw new SecurityException();
            }
            try {
                managerDAO.add(manager);
                System.out.println("Ajout du manager id : " + (i + 1));
            } catch (SQLException e) {
                System.err.println("L'ajout du manager " + (i + 1) + " a échouée. " + e.getMessage());
                throw new SecurityException();
            }
        }

        for (int i = 0; i < NBPROGS; i++) {
            Programmeur prog;
            try {
                prog = new MapProgrammeurFromAPI(NBMANAGERS, programmeurDAO, managerDAO).map();

            } catch (Exception e) {
                System.err.println("La récupération des données pour le programmeur " + (i + 1) + " a échouée. " + e.getMessage());
                throw new SecurityException();
            }
            try {
                programmeurDAO.add(prog);
                System.out.println("Ajout du programmeur id : " + (i + 1));
            } catch (SQLException e) {
                System.err.println("L'ajout du programmeur " + (i + 1) + " a échouée. " + e.getMessage());
                throw new SecurityException();
            }
        }
    }
}