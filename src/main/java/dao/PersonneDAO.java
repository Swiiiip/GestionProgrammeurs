package dao;

import data.db.Actions;
import personnes.Personne;
import personnes.utils.Coords;
import personnes.utils.Pictures;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class PersonneDAO<T extends Personne> {

    private final Actions<T> actions = new Actions<>();

    public abstract List<T> getAll() throws SQLException;

    public abstract T getById(int id) throws SQLException;

    public abstract T getByFullName(String lastName, String firstName) throws SQLException;

    public abstract void add(T personne) throws SQLException;

    public abstract void setSalaryById(int id, float newSalary) throws SQLException;

    public abstract int getCount() throws SQLException;

    public abstract T getWithMaxSalary() throws SQLException;

    public abstract T getWithMinSalary() throws SQLException;

    public abstract Map<Integer, Float> getAvgSalaryByAge() throws SQLException;

    public abstract Map<Integer, T> getRankBySalary() throws SQLException;

    public abstract Map<Float, Integer> getSalaryHistogram() throws SQLException;

    public abstract Map<String, Float> getAverageSalaryByGender() throws SQLException;

    public abstract void deleteById(int id) throws SQLException;

    public abstract void deleteAll() throws SQLException;

    public abstract void resetIndex() throws SQLException;

    public abstract String getTypeLabel();

    public void deleteUtils() throws SQLException {
        actions.deleteUtils();
    }

    public void addPictures(Pictures pictures) throws SQLException {
        actions.addPictures(pictures);
    }

    public void addCoords(Coords coords) throws SQLException {
        actions.addCoords(coords);
    }

    public Pictures getPictures(Pictures pictures) throws SQLException {
        return actions.getPictures(pictures);
    }

    public Coords getCoords(Coords coords) throws SQLException {
        return actions.getCoords(coords);
    }

    public Pictures getPicturesById(int id) throws SQLException {
        return actions.getPicturesById(id);
    }

    public Coords getCoordsById(int id) throws SQLException {
        return actions.getCoordsById(id);
    }

    public void exit() {
        actions.exit();
    }

}
