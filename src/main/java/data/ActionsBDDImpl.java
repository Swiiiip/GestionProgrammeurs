package data;

import javafx.application.Platform;
import utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static javafx.application.Platform.exit;

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
     * Mappe les données récupérées de la requête SQL dans l'objet ProgrammeurBean.
     *
     * @param res Le ResultSet contenant les données de la requête SQL.
     * @return une instance de ProgrammeurBean contenant les données mappées.
     * @throws SQLException Si une erreur SQL survient lors de l'accès à la base de données.
     */
    private ProgrammeurBean mapFromSQLToJava(ResultSet res) throws SQLException {
        ProgrammeurBean prog = new ProgrammeurBean();

        prog.setId(res.getLong("Id"));
        prog.setFirstName(res.getString("FirstName"));
        prog.setLastName(res.getString("LastName"));
        prog.setAddress(res.getString("Address"));
        prog.setPseudo(res.getString("Pseudo"));
        prog.setManager(res.getString("Manager"));
        prog.setHobby(res.getString("Hobby"));
        prog.setBirthYear(res.getInt("BirthYear"));
        prog.setSalary(res.getFloat("Salary"));
        prog.setPrime(res.getFloat("Prime"));

        return prog;
    }

       @Override
    public List<ProgrammeurBean> getAllProg() throws SQLException {
        List<ProgrammeurBean> programmeurs = new ArrayList<>();

        PreparedStatement statement = Constants.CONNECTION.prepareStatement(Constants.GETALLPROG);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            ProgrammeurBean prog = mapFromSQLToJava(resultSet);
            programmeurs.add(prog);
        }

        return programmeurs;
    }

    @Override
    public ProgrammeurBean getProgById(long Id) throws SQLException {
        ProgrammeurBean prog;

        PreparedStatement statement = Constants.CONNECTION.prepareStatement(Constants.GETPROGBYID);

        statement.setLong(1, Id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            prog = mapFromSQLToJava(resultSet);
            return prog;
        }

        resultSet.close();
        statement.close();

        return null;
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
