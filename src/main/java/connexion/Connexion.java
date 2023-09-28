package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La classe Connexion est la classe dédiée au setup de la connexion.
 * Elle configure le driver et établie la connexion.
 *
 * @author Cédric Alonso
 * @author Jade Hatoum
 */
public class Connexion {
    /**
     * URL de la base de données.
     */
    private final String db_url;

    /**
     * Identifiant pour la connexion à la base de données.
     */
    private final String db_user;

    /**
     * Mot de passe pour la connexion à la base de données.
     */
    private final String db_pwd;

    /**
     * Connexion à la base de données.
     */
    private final Connection connexion;

    /**
     * Constructeur de la classe Connexion avec des valeurs par défaut.
     * L'url JDBC par défaut est configuré sur du 'localhost', le port par défaut pour MySQL '3306',avec la BDD sélectionnée par défaut : APTN61_BD
     * L'utilisateur est identique au mot de passe : 'adm'
     */
    public Connexion(){
        this("jdbc:mysql://localhost:3306/APTN61_BD", "adm", "adm");
    }

    /**
     * Constructeur de la classe Connexion.
     *
     * @param db_url L'url de la base de données.
     * @param db_user L'utilisateur pour accéder à la base de données.
     * @param db_pwd Le mot de passe pour accéder à la base de données.
     */
    public Connexion(String db_url, String db_user, String db_pwd){
        this.db_url = db_url;
        this.db_user = db_user;
        this.db_pwd = db_pwd;
        try {
            this.connexion = DriverManager.getConnection(this.db_url, this.db_user, this.db_pwd);
        }catch(SQLException e){
            System.err.println("Error SQL : "+e.getMessage());
            System.err.println("La connexion à la base de données a échoué !");
            System.err.println("Le programme doit fermer !");
            throw new SecurityException();
        }
    }

    /** Récupère l'instance de la connexion lancée.
     *
     * @return L'instance de la connexion lancée.
     */
    public Connection getConnexion(){
        return this.connexion;
    }

    /**
     * Ferme la session de connexion initialisée.
     */
    public void close(){
        try {
            if (this.connexion != null)
                this.connexion.close();

        } catch (SQLException e) {
            System.err.print("Erreur lors de la fermeture de la connexion à la base de données");
        }
    }
}