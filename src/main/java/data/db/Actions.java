package data.db;

import connexion.Connexion;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;
import personnes.utils.Address;
import personnes.utils.Coords;
import personnes.utils.Pictures;
import utils.Departments;
import utils.Gender;
import utils.Hobbies;
import utils.Title;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Actions<T extends Personne> {

    protected final Connexion connexion;

    public Actions() {
        this.connexion = new Connexion();
    }

    private Pictures mapPictures(ResultSet res) throws SQLException {
        Pictures pictures = new Pictures();

        pictures.setId(res.getInt("Id"));
        pictures.setLarge(res.getString("Large"));
        pictures.setMedium(res.getString("medium"));
        pictures.setThumbnail(res.getString("thumbnail"));

        return pictures;
    }

    private Coords mapCoords(ResultSet res) throws SQLException {
        Coords coords = new Coords();

        coords.setId(res.getInt("Id"));
        coords.setLatitude(res.getFloat("Latitude"));
        coords.setLongitude(res.getFloat("Longitude"));

        return coords;
    }

    private Address mapAddress(ResultSet res) throws SQLException{
        Address address = new Address();

        address.setId(res.getInt("Id"));
        address.setStreetNumber(res.getInt("StreetNumber"));
        address.setStreetName(res.getString("StreetName"));
        address.setCity(res.getString("City"));
        address.setState(res.getString("State"));
        address.setCountry(res.getString("Country"));
        address.setPostcode(res.getString("Postcode"));

        return address;
    }

    public Pictures getPicturesById(int idPictures) throws SQLException {
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
            throw new SQLException("L'image avec l'id " + idPictures + " n'existe pas");

        return pictures;
    }

    public Coords getCoordsById(int idCoords) throws SQLException {
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
            throw new SQLException("La coordonnée avec l'id " + idCoords + " n'existe pas");

        return coords;
    }

    public Address getAddressById(int idAddress) throws SQLException{
        Address address = null;
        final String query = "SELECT * FROM Address WHERE Id = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setInt(1, idAddress);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
            address = mapAddress(resultSet);

        resultSet.close();
        statement.close();

        if (address == null)
            throw new SQLException("L'adresse avec l'id " + idAddress + " n'existe pas");

        return address;
    }

    public Coords getFullCoords(Coords coords) throws SQLException {
        Coords fullDataCoords = null;

        final String query = "SELECT * FROM Coords WHERE Latitude = ? AND Longitude = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setFloat(1, coords.getLatitude());
        statement.setFloat(2, coords.getLongitude());

        ResultSet res = statement.executeQuery();

        if (res.next())
            fullDataCoords = mapCoords(res);

        if (fullDataCoords == null)
            throw new SQLException("La coordonnée (" + coords + ") n'existe pas");

        res.close();
        statement.close();

        return fullDataCoords;
    }

    public Pictures getFullPictures(Pictures pictures) throws SQLException {
        Pictures fullDataPictures = null;

        final String query = "SELECT * FROM Pictures WHERE Large = ? AND Medium = ? AND Thumbnail = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setString(1, pictures.getLarge());
        statement.setString(2, pictures.getMedium());
        statement.setString(3, pictures.getThumbnail());

        ResultSet res = statement.executeQuery();

        if (res.next())
            fullDataPictures = mapPictures(res);

        if (fullDataPictures == null)
            throw new SQLException("L'image (" + pictures + ") n 'existe pas.");

        res.close();
        statement.close();

        return fullDataPictures;
    }

    public Address getFullAddress(Address address) throws SQLException {
        Address fullDataAddress = null;

        final String query = "SELECT * FROM Address WHERE StreetNumber = ? AND StreetName = ?" +
                " AND City = ? AND Country = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setInt(1, address.getStreetNumber());
        statement.setString(2, address.getStreetName());
        statement.setString(3, address.getCity());
        statement.setString(4, address.getCountry());

        ResultSet res = statement.executeQuery();

        if (res.next())
            fullDataAddress = mapAddress(res);

        if (fullDataAddress == null)
            throw new SQLException("L'adresse (" + address + ") n 'existe pas.");

        res.close();
        statement.close();

        return fullDataAddress;
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

    private T mapPersonne(String typePersonne, ResultSet resultSet) throws SQLException {
        if ("programmeur".equalsIgnoreCase(typePersonne)) {
            return (T) mapProgrammeur(resultSet);
        } else if ("manager".equalsIgnoreCase(typePersonne)) {
            return (T) mapManager(resultSet);
        } else {
            throw new IllegalArgumentException("Type de personne '" + typePersonne + "' n'est pas reconnu");
        }
    }


    private Manager mapManager(ResultSet res) throws SQLException {
        Manager manager = new Manager();

        manager.setId(res.getInt("Id"));

        String titleStored = res.getString("Title");
        Title title = null;

        for (Title t : Title.values())
            if (t.getTitle().equalsIgnoreCase(titleStored)) {
                title = t;
                break;
            }
        manager.setTitle(title);

        manager.setLastName(res.getString("LastName"));
        manager.setFirstName(res.getString("FirstName"));

        String genderStored = res.getString("Gender");
        Gender gender = null;

        for (Gender g : Gender.values()) {
            if (g.getGender().equalsIgnoreCase(genderStored)) {
                gender = g;
                break;
            }
        }
        manager.setGender(gender);

        Pictures pictures = this.getPicturesById(res.getInt("Id_pictures"));
        manager.setPictures(pictures);

        Coords coords = this.getCoordsById(res.getInt("Id_Coords"));
        manager.setCoords(coords);

        manager.setAddress(this.getAddressById(res.getInt("Id_Address")));

        String hobbyStored = res.getString("Hobby");
        Hobbies hobby = null;
        for (Hobbies h : Hobbies.values())
            if (h.getHobby().equalsIgnoreCase(hobbyStored)) {
                hobby = h;
                break;
            }
        manager.setHobby(hobby);

        String departmentStored = res.getString("Department");
        Departments department = null;
        for (Departments d : Departments.values())
            if (d.getDepartment().equalsIgnoreCase(departmentStored)) {
                department = d;
                break;
            }
        manager.setDepartment(department);

        manager.setBirthYear(res.getInt("BirthYear"));
        manager.setSalary(res.getFloat("Salary"));
        manager.setPrime(res.getFloat("Prime"));

        return manager;
    }

    private Programmeur mapProgrammeur(ResultSet res) throws SQLException {
        Programmeur prog = new Programmeur();

        prog.setId(res.getInt("Id"));

        String titleStored = res.getString("Title");
        Title title = null;
        for (Title t : Title.values())
            if (t.getTitle().equalsIgnoreCase(titleStored)) {
                title = t;
                break;
            }
        prog.setTitle(title);

        prog.setFirstName(res.getString("FirstName"));
        prog.setLastName(res.getString("LastName"));

        String genderStored = res.getString("Gender");
        Gender gender = null;
        for (Gender g : Gender.values())
            if (g.getGender().equalsIgnoreCase(genderStored)) {
                gender = g;
                break;
            }
        prog.setGender(gender);

        prog.setAddress(this.getAddressById(res.getInt("Id_Address")));
        prog.setPseudo(res.getString("Pseudo"));

        Pictures pictures = this.getPicturesById(res.getInt("Id_pictures"));
        prog.setPictures(pictures);

        Coords coords = this.getCoordsById(res.getInt("Id_Coords"));
        prog.setCoords(coords);

        Manager manager = (Manager) this.getById("Manager", res.getInt("Id_manager"));
        prog.setManager(manager);

        String hobbyStored = res.getString("Hobby");
        Hobbies hobby = null;
        for (Hobbies h : Hobbies.values())
            if (h.getHobby().equalsIgnoreCase(hobbyStored)) {
                hobby = h;
                break;
            }
        prog.setHobby(hobby);

        prog.setBirthYear(res.getInt("BirthYear"));
        prog.setSalary(res.getFloat("Salary"));
        prog.setPrime(res.getFloat("Prime"));

        return prog;

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

        if (personne == null && id != 0)
            throw new SQLException("Le " + typePersonne + " avec l'id " + id + " n'existe pas");

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
            throw new SQLException("Le " + typePersonne + " " + lastName + " " + firstName + " n'existe pas");

        return personne;
    }

    public void addPersonne(T personne) throws SQLException {
        String typePersonne = personne.getClass().getSimpleName();

        LinkedHashMap<String, Object> columns = personne.getColumns();
        String query = getQuery(columns, typePersonne);

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        int index = 1;
        for (Object columnValue : columns.values()) {
            statement.setObject(index, columnValue);
            index++;
        }

        statement.executeUpdate();
        statement.close();
    }

    private String getQuery(LinkedHashMap<String, Object> columns, String typePersonne) {
        StringBuilder columnNames = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (Map.Entry<String, Object> entry : columns.entrySet()) {
            String columnName = entry.getKey();

            if (!columnNames.isEmpty()) {
                columnNames.append(", ");
                placeholders.append(", ");
            }
            columnNames.append(columnName);
            placeholders.append("?");
        }

        return "INSERT INTO " + typePersonne + " (" + columnNames + ") VALUES (" + placeholders + ")";
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


    public void addCoords(Coords coords) throws SQLException {
        final String query = "INSERT INTO Coords (Latitude, Longitude)" +
                " VALUES (?, ?)";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setFloat(1, coords.getLatitude());
        statement.setFloat(2, coords.getLongitude());

        statement.executeUpdate();
        statement.close();
    }

    public void addAddress(Address address) throws SQLException{
        final String query = "INSERT INTO Address(StreetNumber, StreetName, City, Country)" +
                " VALUES (?, ?, ?, ?)";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setInt(1, address.getStreetNumber());
        statement.setString(2, address.getStreetName());
        statement.setString(3, address.getCity());
        statement.setString(4, address.getCountry());

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

    public int getCount(String typePersonne) throws SQLException {
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


    public void deleteById(String typePersonne, int id) throws SQLException {
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

    public void deleteUtils() throws SQLException {
        deletePictures();
        deleteCoords();
        deleteAddress();
    }

    private void deletePictures() throws SQLException {
        String query = "DELETE FROM Pictures";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    private void deleteCoords() throws SQLException {
        String query = "DELETE FROM Coords";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    private void deleteAddress() throws SQLException {
        String query = "DELETE FROM Address";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    public void resetIndex(String typePersonne) throws SQLException {
        resetIndexPictures();
        resetIndexCoords();
        resetIndexAddress();

        String query = "ALTER TABLE " + typePersonne + " AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    public void resetIndexPictures() throws SQLException {
        String query = "ALTER TABLE Pictures AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    public void resetIndexCoords() throws SQLException {
        String query = "ALTER TABLE Coords AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    public void resetIndexAddress() throws SQLException {
        String query = "ALTER TABLE Address AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    //Stats

    public T getWithAggregatedSalary(String typePersonne, String aggregation) throws SQLException {
        T personne = null;

        final String query = "SELECT * FROM " + typePersonne +
                " WHERE Salary = (SELECT " + aggregation + "(Salary)" +
                " FROM " + typePersonne + ")";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next())
            personne = mapPersonne(typePersonne, resultSet);

        resultSet.close();
        statement.close();

        if (personne == null)
            throw new SQLException("Aucun " + typePersonne + " ne correspond à votre demande");

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

    public Map<Integer, T> getRankBySalary(String typePersonne) throws SQLException {
        final String query = "SELECT *, DENSE_RANK() OVER (ORDER BY Salary DESC) AS salaryRank" +
                " FROM " + typePersonne +
                " ORDER BY Salary DESC";

        Map<Integer, T> rankBySalary = new HashMap<>();

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            T personne = mapPersonne(typePersonne, resultSet);
            int ranking = resultSet.getInt("salaryRank");

            rankBySalary.put(ranking, personne);
        }
        resultSet.close();
        statement.close();

        return rankBySalary;
    }

    public Map<Float, Integer> getSalaryHistogram(String typePersonne) throws SQLException {
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

    public Map<String, Float> getAverageSalaryByGender(String typePersonne) throws SQLException {
        final String query = "SELECT Gender, AVG(Salary) AS AverageSalary " +
                "FROM " + typePersonne +
                " GROUP BY Gender";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        Map<String, Float> averageSalaryByGender = new HashMap<>();
        while (resultSet.next()) {
            String gender = resultSet.getString("Gender");
            float averageSalary = resultSet.getFloat("AverageSalary");

            averageSalaryByGender.put(gender, averageSalary);
        }

        resultSet.close();
        statement.close();

        return averageSalaryByGender;
    }

    public void exit() {
        this.connexion.close();
    }


}
