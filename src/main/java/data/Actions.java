package data;

import connexion.Connexion;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;
import utils.Coords;
import utils.Pictures;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actions<T extends Personne> {

    private final Connexion connexion;

    public Actions() {
        this.connexion = new Connexion();
    }


    private T mapPersonne(String typePersonne, ResultSet resultSet) throws SQLException {
        return switch (typePersonne) {
            case "Programmeur" -> (T) mapProgrammeur(resultSet);
            case "Manager" -> (T) mapManager(resultSet);
            default -> null;
        };
    }

    private Programmeur mapProgrammeur(ResultSet res) throws SQLException {
        Programmeur prog = new Programmeur();

        prog.setId(res.getInt("Id"));
        prog.setTitle(res.getString("Title"));
        prog.setFirstName(res.getString("FirstName"));
        prog.setLastName(res.getString("LastName"));
        prog.setGender(res.getString("Gender"));
        prog.setAddress(res.getString("Address"));
        prog.setPseudo(res.getString("Pseudo"));

        Pictures pictures = getPicturesById(res.getInt("Id_pictures"));
        prog.setPictures(pictures);

        Coords coords = getCoordsById(res.getInt("Id_Coords"));
        prog.setCoords(coords);

        Manager manager = (Manager) getById("Manager", res.getInt("Id_manager"));
        prog.setManager(manager);

        prog.setHobby(res.getString("Hobby"));
        prog.setBirthYear(res.getInt("BirthYear"));
        prog.setSalary(res.getFloat("Salary"));
        prog.setPrime(res.getFloat("Prime"));

        return prog;
    }

    private Manager mapManager(ResultSet res) throws SQLException {
        Manager manager = new Manager();

        manager.setId(res.getInt("Id"));
        manager.setTitle(res.getString("Title"));
        manager.setLastName(res.getString("LastName"));
        manager.setFirstName(res.getString("FirstName"));
        manager.setGender(res.getString("Gender"));

        Pictures pictures = getPicturesById(res.getInt("Id_pictures"));
        manager.setPictures(pictures);

        Coords coords = getCoordsById(res.getInt("Id_Coords"));
        manager.setCoords(coords);

        manager.setAddress(res.getString("Address"));
        manager.setHobby(res.getString("Hobby"));
        manager.setDepartment(res.getString("Department"));
        manager.setBirthYear(res.getInt("BirthYear"));
        manager.setSalary(res.getFloat("Salary"));
        manager.setPrime(res.getFloat("Prime"));

        return manager;
    }

    private Pictures mapPictures(ResultSet res) throws SQLException{
        Pictures pictures = new Pictures();

        pictures.setId(res.getInt("Id"));
        pictures.setLarge(res.getString("Large"));
        pictures.setMedium(res.getString("medium"));
        pictures.setThumbnail(res.getString("thumbnail"));

        return pictures;
    }

    private Coords mapCoords(ResultSet res) throws SQLException{
        Coords coords = new Coords();

        coords.setId(res.getInt("Id"));
        coords.setLatitude(res.getString("Latitude"));
        coords.setLongitude(res.getString("Longitude"));

        return coords;
    }

    private Pictures getPicturesById(int idPictures) throws SQLException{
        Pictures pictures = null;
        final String query = "SELECT * FROM Pictures WHERE Id = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setInt(1, idPictures);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            pictures = mapPictures(resultSet);
        }

        resultSet.close();
        statement.close();

        if (pictures == null)
            throw new SQLException();

        return pictures;
    }

    private Coords getCoordsById(int idCoords) throws SQLException{
        Coords coords = null;
        final String query = "SELECT * FROM Coords WHERE Id = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setInt(1, idCoords);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            coords = mapCoords(resultSet);
        }

        resultSet.close();
        statement.close();

        if (coords == null)
            throw new SQLException();

        return coords;
    }


    public List<T> getAll(String typePersonne) throws SQLException {
        List<T> personnes = new ArrayList<>();

        final String query = "SELECT * FROM " + typePersonne;
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            T personne = mapPersonne(typePersonne, resultSet);
            personnes.add(personne);
        }

        if (personnes.isEmpty()) {
            throw new SQLException("Il n'y a aucun " + typePersonne + "s dans notre base de données...");
        }

        statement.close();
        resultSet.close();

        return personnes;
    }

    public T getById(String typePersonne, int id) throws SQLException {
        T personne = null;

        final String query = "SELECT * FROM " + typePersonne + " WHERE Id = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            personne = mapPersonne(typePersonne, resultSet);
        }

        resultSet.close();
        statement.close();

        if (personne == null && id != 0) {
            throw new SQLException();
        }

        return personne;
    }

    public T getByFullName(String typePersonne, String lastName, String firstName) throws SQLException {
        final String query = "SELECT * " +
                "FROM " + typePersonne +
                " WHERE LastName = ? AND FirstName = ?";

        T personne = null;

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setString(1, lastName);
        statement.setString(2, firstName);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
            personne = mapPersonne(typePersonne, resultSet);

        resultSet.close();
        statement.close();

        if (personne == null)
            throw new SQLException();

        return personne;
    }

    public void addProg(Programmeur prog) throws SQLException {
        final String query = "INSERT INTO Programmeur (Title, LastName, FirstName, Gender, Id_pictures, Address, Id_Coords, Pseudo, Id_manager, Hobby, BirthYear, Salary, Prime)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setString(1, prog.getTitle());
        statement.setString(2, prog.getLastName());
        statement.setString(3, prog.getFirstName());
        statement.setString(4, prog.getGender());
        statement.setInt(5, prog.getPictures().getId());
        statement.setString(6, prog.getAddress());
        statement.setInt(7, prog.getCoords().getId());
        statement.setString(8, prog.getPseudo());
        statement.setInt(9, prog.getManager().getId());
        statement.setString(10, prog.getHobby());
        statement.setInt(11, prog.getBirthYear());
        statement.setFloat(12, prog.getSalary());
        statement.setFloat(13, prog.getPrime());

        statement.executeUpdate();
        statement.close();

    }

    public void addManager(Manager manager) throws SQLException {
        final String query = "INSERT INTO Manager (Title, LastName, FirstName, Gender, Id_pictures, Address, Id_Coords, Hobby, Department, BirthYear, Salary, Prime)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setString(1, manager.getTitle());
        statement.setString(2, manager.getLastName());
        statement.setString(3, manager.getFirstName());
        statement.setString(4, manager.getGender());
        statement.setInt(5, manager.getPictures().getId());
        statement.setString(6, manager.getAddress());
        statement.setInt(7, manager.getCoords().getId());
        statement.setString(8, manager.getHobby());
        statement.setString(9, manager.getDepartment());
        statement.setInt(10, manager.getBirthYear());
        statement.setFloat(11, manager.getSalary());
        statement.setFloat(12, manager.getPrime());

        statement.executeUpdate();
        statement.close();
    }

    public void addPictures(Pictures pictures) throws SQLException {
        final String query = "INSERT INTO Pictures (Large, Medium, Thumbnail) VALUES (?, ?, ?)";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setString(1, pictures.getLarge());
        statement.setString(2, pictures.getMedium());
        statement.setString(3, pictures.getThumbnail());

        statement.executeUpdate();
        statement.close();
    }


    public void addCoords(Coords coords) throws SQLException{
        final String query = "INSERT INTO Coords (Latitude, Longitude)" +
                " VALUES (?, ?)";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setString(1, coords.getLatitude());
        statement.setString(2, coords.getLongitude());

        statement.executeUpdate();
        statement.close();
    }

    public void setSalaryById(String typePersonne, int id, float newSalary) throws SQLException {
        this.getById(typePersonne, id);

        final String query = "UPDATE " + typePersonne + " SET Salary = ? WHERE Id = ?";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setDouble(1, newSalary);
        statement.setLong(2, id);

        statement.executeUpdate();

        statement.close();
    }
    public int getCount(String typePersonne) throws SQLException{
        final String query = "SELECT COUNT(*) AS nbPerson FROM " + typePersonne;

        int count = 0;
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
            count = resultSet.getInt("nbPerson");

        resultSet.close();
        statement.close();

        return count;
    }
    public T getWithMaxSalary(String typePersonne) throws SQLException{
        T personne = null;

        final String query = "SELECT * FROM " + typePersonne +
                " WHERE (Salary, Prime) = (SELECT MAX(Salary), MAX(Prime)" +
                " FROM " + typePersonne + ")";


        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next())
            personne = mapPersonne(typePersonne, resultSet);

        resultSet.close();
        statement.close();

        return personne;
    }

    public T getWithMinSalary(String typePersonne) throws SQLException{
        T personne = null;

        final String query = "SELECT * FROM " + typePersonne +
                " WHERE (Salary, Prime) = (SELECT MIN(Salary), MIN(Prime)" +
                " FROM " + typePersonne + ")";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next())
            personne = mapPersonne(typePersonne, resultSet);

        resultSet.close();
        statement.close();

        return personne;
    }

    public Map<Integer, Float> getAvgSalaryByAge(String typePersonne) throws SQLException {
        final String query = "SELECT YEAR(CURRENT_DATE) - BirthYear AS Age, AVG(Salary) AS AverageSalary " +
                "FROM " + typePersonne +
                " GROUP BY Age" +
                " ORDER BY Age ASC";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        Map<Integer, Float> salaryByAge = new HashMap<>();
        while (resultSet.next()) {
            int age = resultSet.getInt("Age");
            float averageSalary = resultSet.getFloat("AverageSalary");

            salaryByAge.put(age, averageSalary);
        }

        resultSet.close();
        statement.close();

        return salaryByAge;
    }

    public Map<Integer, T> getRankBySalary(String typePersonne) throws SQLException{
        final String query = "SELECT *, DENSE_RANK() OVER (ORDER BY Salary DESC) AS salaryRank" +
                " FROM " + typePersonne +
                " ORDER BY Salary DESC";

        Map<Integer, T> rankBySalary = new HashMap<>();

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            T personne = mapPersonne(typePersonne, resultSet);
            int ranking = resultSet.getInt("salaryRank");

            rankBySalary.put(ranking, personne);
        }
        resultSet.close();
        statement.close();

        return rankBySalary;
    }

    public Map<Float, Integer> getSalaryHistogram(String typePersonne) throws SQLException{
        final String query = "SELECT FLOOR(Salary / 1000) * 1000 AS salaryRange, COUNT(*) AS nbPerson" +
                " FROM " + typePersonne +
                " GROUP BY salaryRange" +
                " ORDER BY salaryRange ASC";

        Map<Float, Integer> salaryHistogramManager = new HashMap<>();

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            float salaryRange = resultSet.getFloat("salaryRange");
            int nbPerson = resultSet.getInt("nbPerson");

            salaryHistogramManager.put(salaryRange, nbPerson);
        }

        resultSet.close();
        statement.close();

        return salaryHistogramManager;
    }

    private static double calculateMean(List<Double> data) {
        double sum = 0.0;
        for (double value : data) {
            sum += value;
        }
        return sum / data.size();
    }

    public double getCorrelationBetweenAgeAndSalary(String typePersonne) throws SQLException{
        final String query = "SELECT BirthYear, Salary " +
                "FROM " + typePersonne;

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        List<Double> birthYears = new ArrayList<>();
        List<Double> salaries = new ArrayList<>();

        while (resultSet.next()) {
            birthYears.add(resultSet.getDouble("BirthYear"));
            salaries.add(resultSet.getDouble("Salary"));
        }

        double meanBirthYear = calculateMean(birthYears);
        double meanSalary = calculateMean(salaries);

        double numerator = 0.0;
        double denominatorX = 0.0;
        double denominatorY = 0.0;

        for (int i = 0; i < birthYears.size(); i++) {
            double diffX = birthYears.get(i) - meanBirthYear;
            double diffY = salaries.get(i) - meanSalary;

            numerator += (diffX * diffY);
            denominatorX += (diffX * diffX);
            denominatorY += (diffY * diffY);
        }

        resultSet.close();
        statement.close();

        return numerator / (Math.sqrt(denominatorX) * Math.sqrt(denominatorY));
    }

    public void deleteById(String typePersonne, int id) throws SQLException{
        this.getById(typePersonne, id);

        final String query = "DELETE FROM " + typePersonne + " WHERE Id = ?";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setLong(1, id);
        statement.executeUpdate();

        statement.close();
    }

    public void deleteAll(String typePersonne) throws SQLException {
        String query = "DELETE FROM " + typePersonne;
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    public void deleteUtils() throws SQLException{
        deletePictures();
        deleteCoords();
    }
    private void deletePictures() throws SQLException{
        String query = "DELETE FROM Pictures";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    private void deleteCoords() throws SQLException{
        String query = "DELETE FROM Coords";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    public void resetIndex(String typePersonne) throws SQLException {
        resetIndexPictures();
        resetIndexCoords();

        String query = "ALTER TABLE " + typePersonne + " AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    public void resetIndexPictures() throws SQLException{
        String query = "ALTER TABLE Pictures AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    public void resetIndexCoords() throws SQLException{
        String query = "ALTER TABLE Coords AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    public void exit() {
        this.connexion.close();
    }


    public Pictures getPictures(Pictures pictures) throws SQLException{
        Pictures fullDataPictures = null;

        final String query = "Select * from Pictures WHERE Large = ? " +
                "AND Medium = ? " +
                "AND Thumbnail = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setString(1, pictures.getLarge());
        statement.setString(2, pictures.getMedium());
        statement.setString(3, pictures.getThumbnail());

        ResultSet res = statement.executeQuery();

        if (res.next())
            fullDataPictures = mapPictures(res);

        if (fullDataPictures == null)
            throw new SQLException("Pictures null");

        res.close();
        statement.close();

        return fullDataPictures;
    }

    public Coords getCoords(Coords coords) throws SQLException{
        Coords fullDataCoords = null;

        final String query = "Select * from Coords WHERE Latitude = ? " +
                "AND Longitude = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setString(1, coords.getLatitude());
        statement.setString(2, coords.getLongitude());

        ResultSet res = statement.executeQuery();

        if (res.next())
            fullDataCoords = mapCoords(res);

        if (fullDataCoords == null)
            throw new SQLException("Coords null");

        res.close();
        statement.close();

        return fullDataCoords;
    }
}