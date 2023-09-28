package utils;

/**
 * Cette classe contient des constantes et des méthodes utilitaires liées à la gestion de la base de données
 * et aux attributs des objets ProgrammeurBean.
 * Elle fournit également une connexion à la base de données.
 * Les constantes définies ici sont utilisées dans d'autres parties du programme.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class RequetesSQL {
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
    public static final String ADDPROG = "INSERT INTO Programmeur (LastName, FirstName, Address, Pseudo, Id_manager, Hobby, BirthYear, Salary, Prime)" +
            " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    /**
     * Requête SQL pour mettre à jour le salaire d'un programmeur par son ID.
     */
    public static final String SETPROGSALARYBYID = "UPDATE Programmeur SET Salary = ? WHERE Id = ?";

    public static final String GETPROGWITHMAXSALARY = "SELECT * FROM Programmeur WHERE Salary = (SELECT MAX(Salary) FROM Programmeur)";
    public static final String GETPROGWITHMINSALARY = "SELECT * FROM Programmeur WHERE Salary = (SELECT MAX(Salary) FROM Programmeur)";
    public static final String GETAVGSALARYBYAGEPROG = "SELECT YEAR(CURRENT_DATE) - BirthYear AS Age, AVG(Salary) AS AverageSalary " +
            "FROM Programmeur " +
            "GROUP BY Age " +
            "ORDER BY Age";
    public static final String GETNBPROG = "SELECT COUNT(*) AS nbProgrammeur FROM Programmeur";

    public static final String GETRANKPROGBYSALARY = "SELECT *, " +
            "DENSE_RANK() OVER (ORDER BY Salary DESC) AS ClassementSalaire " +
            "FROM Programmeur ORDER BY Salary DESC";

    public static final String GETCORRELATIONBETWEENAGEANDSALARYPROG = "SELECT BirthYear, Salary " +
                    "FROM Programmeur";




    public static final String GETALLMANAGER = "SELECT * FROM Manager";

    /**
     * Requête SQL pour récupérer un manager par son ID.
     */
    public static final String GETMANAGERBYID = "SELECT * FROM Manager WHERE Id = ?";

    /**
     * Requête SQL pour récupérer un manager par son nom et prénom.
     */
    public static final String GETMANAGERBYFULLNAME = "SELECT * FROM Manager WHERE FirstName = ? AND LastName = ?";

    public static final String DELETEMANAGERBYID = "DELETE FROM Manager WHERE Id = ?";

    public static final String ADDMANAGER = "INSERT INTO Manager (LastName, FirstName, Address, Hobby, Department, BirthYear, Salary, Prime) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String SETMANAGERSALARYBYID = "UPDATE Manager SET Salary = ? WHERE Id = ?";

    public static final String GETHISTOSALARYMANAGER = "SELECT FLOOR(Salary / 1000) * 1000 AS PlageSalaire, " +
            "COUNT(*) AS nbManager " +
            "FROM Manager " +
            "GROUP BY PlageSalaire " +
            "ORDER BY PlageSalaire";


    public static final String DELETEALLPROGS = "DELETE FROM Programmeur";

    public static final String DELETEALLMANAGERS = "DELETE FROM Manager";

    public static final String RESETINDEXPROG = "ALTER TABLE Programmeur AUTO_INCREMENT = 1";

    public static final String RESETINDEXMANAGER = "ALTER TABLE Manager AUTO_INCREMENT = 1";
}
