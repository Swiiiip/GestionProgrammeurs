package dao;

import data.db.Actions;
import personnes.Manager;

import java.sql.SQLException;
import java.util.List;

public class ManagerDAO extends PersonneDAO<Manager> {
    private final Actions<Manager> actions = new Actions<>();

    @Override
    public List<Manager> getAll() throws SQLException {
        return actions.getAll("Manager");
    }

    @Override
    public Manager getById(int id) throws SQLException {
        return actions.getById("Manager", id);
    }

    @Override
    public Manager getByFullName(String lastName, String firstName) throws SQLException {
        return actions.getByFullName("Manager", lastName, firstName);
    }

    @Override
    public void add(Manager manager) throws SQLException {
        actions.addPersonne(manager);
    }

    @Override
    public void setSalaryById(int id, float newSalary) throws SQLException {
        actions.setSalaryById("Manager", id, newSalary);
    }

    @Override
    public void deleteById(int id) throws SQLException {
        actions.deleteById("Manager", id);
    }

    @Override
    public void deleteAll() throws SQLException {
        actions.deleteAll("Manager");
    }

    @Override
    public void resetIndex() throws SQLException {
        actions.resetIndex("Manager");
    }

    @Override
    public String getTypeLabel() {
        return "manager";
    }

}
