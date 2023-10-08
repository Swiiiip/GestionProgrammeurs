package dao;

import data.Actions;
import personnes.Manager;
import utils.Coords;
import utils.Pictures;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ManagerDAO implements PersonneDAO<Manager> {
    private final Actions<Manager> actionsBD = new Actions<>();

    @Override
    public List<Manager> getAll() throws SQLException {
        return actionsBD.getAll("Manager");
    }

    @Override
    public Manager getById(int id) throws SQLException {
        return actionsBD.getById("Manager", id);
    }

    @Override
    public Manager getByFullName(String lastName, String firstName) throws SQLException {
        return actionsBD.getByFullName("Manager", lastName, firstName);
    }

    @Override
    public void add(Manager manager) throws SQLException {
        actionsBD.addPersonne(manager);
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
        return actionsBD.getWithMaxSalary("Manager");
    }

    @Override
    public Manager getWithMinSalary() throws SQLException{
        return actionsBD.getWithMinSalary("Manager");
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
    public Map<String, Float> getAverageSalaryByGender() throws SQLException{
        return actionsBD.getAverageSalaryByGender("Manageur");
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
    public void deleteUtils() throws SQLException{
        actionsBD.deleteUtils();
    }

    @Override
    public void addPictures(Pictures pictures) throws SQLException{
        actionsBD.addPictures(pictures);
    }

    @Override
    public void addCoords(Coords coords) throws SQLException{
        actionsBD.addCoords(coords);
    }

    @Override
    public Pictures getPictures(Pictures pictures) throws SQLException{
        return actionsBD.getPictures(pictures);
    }

    @Override
    public Coords getCoords(Coords coords) throws SQLException{
        return actionsBD.getCoords(coords);
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
