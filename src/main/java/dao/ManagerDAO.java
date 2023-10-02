package dao;

import data.Actions;
import personnes.Manager;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ManagerDAO implements PersonneDAO<Manager> {
    private final Actions actionsBD = new Actions();

    @Override
    public List<Manager> getAll() throws SQLException {
        return actionsBD.getAll("Manager");
    }


    @Override
    public Manager getById(int id) throws SQLException {
        return (Manager) actionsBD.getById("Manager", id);
    }

    @Override
    public Manager getByFullName(String lastName, String firstName) throws SQLException {
        return (Manager) actionsBD.getByFullName("Manager", lastName, firstName);
    }

    @Override
    public void add(Manager manager) throws SQLException {
        actionsBD.addManager(manager);
    }

    @Override
    public void setSalaryById(int id, float newSalary) throws SQLException{
        actionsBD.setSalaryById("Manager", id, newSalary);
    }

    @Override
    public int getCount() throws SQLException{
        return actionsBD.getCount("Manager");
    }

    @Override
    public Manager getWithMaxSalary() throws SQLException{
        return (Manager) actionsBD.getWithMaxSalary("Manager");
    }

    @Override
    public Manager getWithMinSalary() throws SQLException{
        return (Manager) actionsBD.getWithMinSalary("Manager");
    }

    @Override
    public Map<Integer, Float> getAvgSalaryByAge() throws SQLException {
        return actionsBD.getAvgSalaryByAge("Manager");
    }

    @Override
    public Map<Integer, Manager> getRankBySalary() throws SQLException{
        return actionsBD.getRankBySalary("Manager");
    }

    @Override
    public Map<Float, Integer> getSalaryHistogram() throws SQLException{
        return actionsBD.getSalaryHistogram("Manager");
    }

    @Override
    public double getCorrelationBetweenAgeAndSalary() throws SQLException{
        return actionsBD.getCorrelationBetweenAgeAndSalary("Manager");
    }


    @Override
    public void deleteById(int id) throws SQLException {
        actionsBD.deleteById("Manager", id);
    }


    @Override
    public void deleteAll() throws SQLException {
        actionsBD.deleteAll("Manager");
    }

    @Override
    public void resetIndex() throws SQLException {
        actionsBD.resetIndex("Manager");
    }

    @Override
    public void exit() {
        actionsBD.exit();
    }
}
