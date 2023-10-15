package dao;

import data.db.Actions;
import personnes.Programmeur;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
    public int getCount() throws SQLException {
        return actions.getCount("Programmeur");
    }

    @Override
    public Programmeur getWithMaxSalary() throws SQLException {
        return actions.getWithAggregatedSalary("Programmeur", "MAX");
    }

    @Override
    public Programmeur getWithMinSalary() throws SQLException {
        return actions.getWithAggregatedSalary("Programmeur", "MIN");
    }

    @Override
    public Map<Integer, Float> getAvgSalaryByAge() throws SQLException {
        return actions.getAvgSalaryByAge("Programmeur");
    }

    @Override
    public Map<Integer, Programmeur> getRankBySalary() throws SQLException {
        return actions.getRankBySalary("Programmeur");
    }

    @Override
    public Map<Float, Integer> getSalaryHistogram() throws SQLException {
        return actions.getSalaryHistogram("Programmeur");
    }

    @Override
    public Map<String, Float> getAverageSalaryByGender() throws SQLException {
        return actions.getAverageSalaryByGender("Programmeur");
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