package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Cette classe gère la connexion à une base de données en utilisant JDBC (Java Database Connectivity).
 * Elle permet d'initialiser et de maintenir une connexion à une base de données MySQL.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class Connexion {

    /**
     * L'objet de connexion à la base de données.
     */
    private final Connection connexion;

    /**
     * Constructeur par défaut de la classe Connexion, utilisant des informations de connexion par défaut.
     * Cette méthode crée une connexion à la base de données MySQL avec les paramètres par défaut.
     * Par défaut, elle se connecte à la base de données "APTN61_BD" sur le serveur MySQL local,
     * avec l'utilisateur "adm" et le mot de passe "adm".
     */
    public Connexion() {
        this("jdbc:mysql://localhost:3306/APTN61_BD", "adm", "adm");
    }

    /**
     * Constructeur de la classe Connexion.
     * Cette méthode crée une connexion à la base de données MySQL en utilisant les informations spécifiées.
     *
     * @param db_url  L'URL de la base de données à laquelle se connecter.
     * @param db_user Le nom d'utilisateur pour la connexion à la base de données.
     * @param db_pwd  Le mot de passe pour la connexion à la base de données.
     */
    public Connexion(String db_url, String db_user, String db_pwd) {
        try {
            this.connexion = DriverManager.getConnection(db_url, db_user, db_pwd);
        } catch (SQLException e) {
            throw new RuntimeException("La connexion à la base de données a échoué !", e);
        }
    }

    /**
     * Obtient l'objet de connexion à la base de données.
     *
     * @return L'objet de connexion à la base de données.
     */
    public Connection getConnexion() {
        return connexion;
    }

    /**
     * Ferme la connexion à la base de données.
     * Cette méthode doit être appelée pour libérer les ressources après utilisation de la connexion.
     */
    public void close() {
        try {
            if (connexion != null) {
                connexion.close();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la fermeture de la connexion à la base de données");
        }
    }
}
