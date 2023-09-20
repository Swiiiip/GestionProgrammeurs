package data;

import utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe implémente l'interface ActionsBDD et fournit des méthodes pour effectuer
 * des opérations liées à une base de données de programmeurs.
 * Elle utilise la connexion définie dans la classe Constants pour interagir avec la base de données.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class ActionsBDDImpl implements ActionsBDD {

    /**
     * Crée un objet ProgrammeurBean à partir d'un ResultSet.
     *
     * @param result Le ResultSet contenant les données du programmeur.
     * @return Un objet ProgrammeurBean initialisé avec les données du ResultSet.
     * @throws SQLException Si une erreur SQL survient lors de la récupération des données.
     */
    private ProgrammeurBean createProg(ResultSet result) throws SQLException {
        ProgrammeurBean prog = new ProgrammeurBean();

        prog.setId(result.getLong("Id"));
        prog.setFirstName(result.getString("firstName"));
        prog.setLastName(result.getString("LastName"));
        prog.setAddress(result.getString("Address"));
        prog.setPseudo(result.getString("Pseudo"));
        prog.setManager(result.getString("Manager"));
        prog.setHobby(result.getString("Hobby"));
        prog.setBirthYear(result.getInt("BirthYear"));
        prog.setSalary(result.getFloat("Salary"));
        prog.setPrime(result.getFloat("Prime"));

        return prog;
    }

    @Override
    public List<ProgrammeurBean> getAllProg() throws SQLException {
        List<ProgrammeurBean> programmeurs = new ArrayList<>();

        PreparedStatement statement = Constants.CONNECTION.prepareStatement(Constants.GETALLPROG);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            programmeurs.add(createProg(resultSet));
        }

        resultSet.close();
        statement.close();

        return programmeurs;
    }

    @Override
    public ProgrammeurBean getProgById(long Id) throws SQLException {
        ProgrammeurBean prog = null;

        PreparedStatement statement = Constants.CONNECTION.prepareStatement(Constants.GETPROGBYID);

        statement.setLong(1, Id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            prog = createProg(resultSet);
        }

        resultSet.close();
        statement.close();

        return prog;
    }

    @Override
    public void deleteProgById(long id) throws SQLException {
        PreparedStatement statement = Constants.CONNECTION.prepareStatement(Constants.DELETEPROGBYID);

        statement.setLong(1, id);

        statement.executeUpdate();

        statement.close();
    }

    @Override
    public void addProg(ProgrammeurBean programmeur) throws SQLException {
        PreparedStatement statement = Constants.CONNECTION.prepareStatement(Constants.ADDPROG);

        statement.setString(1, programmeur.getLastName());
        statement.setString(2, programmeur.getFirstName());
        statement.setString(3, programmeur.getAddress());
        statement.setString(4, programmeur.getPseudo());
        statement.setString(5, programmeur.getManager());
        statement.setString(6, programmeur.getHobby());
        statement.setInt(7, programmeur.getBirthYear());
        statement.setFloat(8, programmeur.getSalary());
        statement.setFloat(9, programmeur.getPrime());

        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void setSalaryById(long id, double newSalary) throws SQLException {
        PreparedStatement statement = Constants.CONNECTION.prepareStatement(Constants.SETSALARYBYID);

        statement.setDouble(1, newSalary);
        statement.setLong(2, id);

        statement.executeUpdate();

        statement.close();
    }

    @Override
    public void exit() {
        try {
            if (Constants.CONNECTION != null) {
                Constants.CONNECTION.close();
            }
        } catch (SQLException e) {
            System.err.print("Erreur lors de la fermeture de la connexion à la base de données");
        }
        System.exit(0);
    }
}
