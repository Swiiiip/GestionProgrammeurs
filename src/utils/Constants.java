package utils;

import data.ProgrammeurBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Cette classe contient des constantes et des méthodes utilitaires liées à la gestion de la base de données
 * et aux attributs des objets ProgrammeurBean.
 * Elle fournit également une connexion à la base de données.
 * Les constantes définies ici sont utilisées dans d'autres parties du programme.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class Constants {

    /**
     * Liste des noms d'attributs pour les objets ProgrammeurBean.
     */
    public static final List<String> ATTRIBUTES = Arrays.asList("Id", "Last Name", "First Name", "Address", "Pseudo",
            "Manager", "Hobby", "Birth Year", "Salary", "Prime");

    /**
     * Longueur maximale des messages d'affichage basée sur la longueur des attributs.
     */
    public static final int MSGLEN = ATTRIBUTES.stream().map(String::length).max(Integer::compareTo).get();

    /**
     * Obtient une liste des attributs d'un objet ProgrammeurBean sous forme de chaînes.
     *
     * @param prog L'objet ProgrammeurBean à partir duquel extraire les attributs.
     * @return Une liste de chaînes représentant les attributs de l'objet ProgrammeurBean.
     */
    public static List<String> ListAttributes(ProgrammeurBean prog) {
        return Arrays.asList(
                String.valueOf(prog.getId()),
                prog.getLastName(),
                prog.getFirstName(),
                prog.getAddress(),
                prog.getPseudo(),
                prog.getManager(),
                prog.getHobby(),
                String.valueOf(prog.getBirthYear()),
                String.valueOf(prog.getSalary()),
                String.valueOf(prog.getPrime())
        );
    }

    /**
     * URL de la base de données.
     */
    public static final String DB_URL = "jdbc:mysql://localhost:3306/APTN61_BD";

    /**
     * Identifiant pour la connexion à la base de données.
     */
    public static final String DB_ID = "adm";

    /**
     * Mot de passe pour la connexion à la base de données.
     */
    public static final String DB_PASSWORD = "adm";

    /**
     * Connexion à la base de données.
     */
    public static final Connection CONNECTION;

    static {
        try {
            CONNECTION = DriverManager.getConnection(DB_URL, DB_ID, DB_PASSWORD);
        } catch (SQLException e) {
            System.err.println("La connexion à la base de données a échoué !");
            System.err.println("Le programme doit fermer !");
            throw new SecurityException();
        }
    }

    /**
     * Requête SQL pour récupérer tous les programmeurs de la base de données.
     */
    public static final String GETALLPROG = "SELECT * FROM Programmeur";

    /**
     * Requête SQL pour récupérer un programmeur par son ID.
     */
    public static final String GETPROGBYID = "SELECT * FROM Programmeur WHERE Id = ?";

    /**
     * Requête SQL pour supprimer un programmeur par son ID.
     */
    public static final String DELETEPROGBYID = "DELETE FROM Programmeur WHERE Id = ?";

    /**
     * Requête SQL pour ajouter un nouveau programmeur dans la base de données.
     */
    public static final String ADDPROG = "INSERT INTO Programmeur (LastName, FirstName, Address, Pseudo, Manager, Hobby, BirthYear, Salary, Prime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Requête SQL pour mettre à jour le salaire d'un programmeur par son ID.
     */
    public static final String SETSALARYBYID = "UPDATE Programmeur SET salary = ? WHERE Id = ?";
}
