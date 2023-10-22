package dao;

import data.db.Actions;
import personnes.Programmeur;

import java.sql.SQLException;
import java.util.List;

public class ProgrammeurDAO extends PersonneDAO<Programmeur> {
    private final Actions<Programmeur> actions = new Actions<>();

    @Override
    public List<Programmeur> getAll() throws SQLException {
        return actions.getAll("Programmeur");
    }

    @Override
    public Programmeur getById(int id) throws SQLException {
        return actions.getById("Programmeur", id);
    }

    @Override
    public Programmeur getByFullName(String lastName, String firstName) throws SQLException {
        return actions.getByFullName("Programmeur", lastName, firstName);
    }

    @Override
    public void add(Programmeur prog) throws SQLException {
        actions.addPersonne(prog);
    }

    @Override
    public void setSalaryById(int id, float newSalary) throws SQLException {
        actions.setSalaryById("Programmeur", id, newSalary);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        actions.deleteById("Programmeur", id);
    }

    @Override
    public void deleteAll() throws SQLException {
        actions.deleteAll("Programmeur");
    }


    @Override
    public void resetIndex() throws SQLException {
        actions.resetIndex("Programmeur");
    }

    @Override
    public String getTypeLabel() {
        return "programmeur";
    }

}