package utils;

/**
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class RequetesSQL {
    /**
     * Requête SQL pour ajouter un nouveau programmeur dans la base de données.
     */
    public static final String ADDPROG = "INSERT INTO Programmeur (LastName, FirstName, Gender, Address, Pseudo, Id_manager, Hobby, BirthYear, Salary, Prime)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    /**
     * Requête SQL pour ajouter un nouveau manager dans la base de données.
     */
    public static final String ADDMANAGER = "INSERT INTO Manager (LastName, FirstName, Gender, Address, Hobby, Department, BirthYear, Salary, Prime) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

}
