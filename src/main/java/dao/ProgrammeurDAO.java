package dao;

import data.Actions;
import personnes.Programmeur;
import utils.Coords;
import utils.Pictures;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProgrammeurDAO implements PersonneDAO<Programmeur> {
    private final Actions<Programmeur> actionsBD = new Actions<>();

    @Override
    public List<Programmeur> getAll() throws SQLException {
        return actionsBD.getAll("Programmeur");
    }

    @Override
    public Programmeur getById(int id) throws SQLException {
        return actionsBD.getById("Programmeur", id);
    }

    @Override
    public Programmeur getByFullName(String lastName, String firstName) throws SQLException {
        return actionsBD.getByFullName("Programmeur", lastName, firstName);
    }

    @Override
    public void add(Programmeur prog) throws SQLException{
        actionsBD.addProg(prog);
    }

    @Override
    public void setSalaryById(int id, float newSalary) throws SQLException{
        actionsBD.setSalaryById("Programmeur", id, newSalary);
    }

    @Override
    public int getCount() throws SQLException{
        return actionsBD.getCount("Programmeur");
    }

    @Override
    public Programmeur getWithMaxSalary() throws SQLException{
        return actionsBD.getWithMaxSalary("Programmeur");
    }

    @Override
    public Programmeur getWithMinSalary() throws SQLException{
        return actionsBD.getWithMinSalary("Programmeur");
    }

    @Override
    public Map<Integer, Float> getAvgSalaryByAge() throws SQLException{
        return actionsBD.getAvgSalaryByAge("Programmeur");
    }

    @Override
    public Map<Integer, Programmeur> getRankBySalary() throws SQLException{
        return actionsBD.getRankBySalary("Programmeur");
    }

    @Override
    public Map<Float, Integer> getSalaryHistogram() throws SQLException{
        return actionsBD.getSalaryHistogram("Programmeur");
    }

    @Override
    public double getCorrelationBetweenAgeAndSalary() throws SQLException {
        return actionsBD.getCorrelationBetweenAgeAndSalary("Programmeur");
    }
    @Override
    public void deleteById(int id) throws SQLException {
        actionsBD.deleteById("Programmeur", id);
    }

    @Override
    public void deleteAll() throws SQLException {
        actionsBD.deleteAll("Programmeur");
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
        actionsBD.resetIndex("Programmeur");
    }

    @Override
    public void exit() {
        actionsBD.exit();
    }
}