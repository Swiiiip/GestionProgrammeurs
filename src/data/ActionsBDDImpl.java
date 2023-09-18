package data;

import utils.Constants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActionsBDDImpl implements ActionsBDD {

    private ProgrammeurBean createProg(ResultSet result) throws SQLException {
        ProgrammeurBean prog = null;

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

        statement.setLong (1, id);

        statement.executeUpdate();

        statement.close();
    }

    @Override
    public void addProg(ProgrammeurBean programmeur) throws SQLException {
        PreparedStatement statement = Constants.CONNECTION.prepareStatement(Constants.ADDPROG);

        statement.setLong(1, programmeur.getId());
        statement.setString(2, programmeur.getLastName());
        statement.setString(3, programmeur.getFirstName());
        statement.setString(4, programmeur.getAddress());
        statement.setString(5, programmeur.getPseudo());
        statement.setString(6, programmeur.getManager());
        statement.setString(7, programmeur.getHobby());
        statement.setInt(8, programmeur.getBirthYear());
        statement.setFloat(9, programmeur.getSalary());
        statement.setFloat(10, programmeur.getPrime());

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
        System.exit(0);
    }
}
