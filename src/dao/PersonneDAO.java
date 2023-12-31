package dao;

import data.db.Actions;
import personnes.Personne;
import personnes.utils.Address;
import personnes.utils.Coords;
import personnes.utils.Pictures;

import java.sql.SQLException;
import java.util.List;

public abstract class PersonneDAO<T extends Personne> {

    private final Actions<T> actions = new Actions<>();

    public abstract List<T> getAll() throws SQLException;

    public abstract T getById(int id) throws SQLException;

    public abstract T getByFullName(String lastName, String firstName) throws SQLException;

    public abstract void add(T personne) throws SQLException;

    public abstract void setSalaryById(int id, float newSalary) throws SQLException;

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

    public void addAddress(Address address) throws SQLException{
        actions.addAddress(address);
    }

    public Pictures getPictures(Pictures pictures) throws SQLException {
        return actions.getFullPictures(pictures);
    }

    public Coords getCoords(Coords coords) throws SQLException {
        return actions.getFullCoords(coords);
    }

    public Address getAddress(Address address) throws SQLException{
        return actions.getFullAddress(address);
    }

    public Pictures getPicturesById(int id) throws SQLException {
        return actions.getPicturesById(id);
    }
    public void exit() {
        actions.exit();
    }

}
