package connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

    private final Connection connexion;

    public Connexion() {
        this("jdbc:mysql://localhost:3306/APTN61_BD", "adm", "adm");
    }

    public Connexion(String db_url, String db_user, String db_pwd) {
        try {
            this.connexion = DriverManager.getConnection(db_url, db_user, db_pwd);
        } catch (SQLException e) {
            throw new RuntimeException("La connexion à la base de données a échoué !", e);
        }
    }

    public Connection getConnexion() {
        return connexion;
    }

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
