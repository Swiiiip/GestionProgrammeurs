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

/**
 * La classe {@code Actions} fournit un ensemble de méthodes pour effectuer des opérations de base de données
 * liées à la gestion des personnes, notamment des managers et des programmeurs. Elle gère également les entités de données associées telles que les images, les coordonnées et les adresses.
 *
 * <p>Cette classe est conçue pour interagir avec une base de données relationnelle et offre diverses opérations
 * pour gérer et extraire des informations sur les personnes, y compris la requête et la modification de leurs données.
 * Elle est destinée à être utilisée dans des applications nécessitant la gestion de bases de données de dossiers de personnel.
 *
 * <p>La classe prend en charge les entités de base de données suivantes :
 * - {@code Programmeur} : Représente un programmeur.
 * - {@code Manager} : Représente un manager.
 * - {@code Pictures} : Représente les images associées aux personnes.
 * - {@code Coords} : Représente les coordonnées géographiques.
 * - {@code Address} : Représente les adresses postales.
 *
 * <p>Cette classe utilise la classe {@link connexion.Connexion} pour établir et gérer la connexion à la base de données.
 * Elle exploite également d'autres classes utilitaires telles que {@link personnes.utils.Pictures},
 * {@link personnes.utils.Coords} et {@link personnes.utils.Address} pour travailler avec des types de données spécifiques.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 * @version 4.7
 */
public class Actions<T extends Personne> {

    /**
     * La connexion à la base de données utilisée pour interagir avec les données des personnes.
     */
    public final Connexion connexion;


    /**
     * Construit un objet {@code Actions}, initialisant la connexion à la base de données.
     */
    public Actions() {
        this.connexion = new Connexion();
    }

    /**
     * Mappe les données d'un ResultSet à un objet Pictures.
     *
     * @param res Le ResultSet contenant les données de l'image.
     * @return Un objet Pictures contenant les données mappées.
     * @throws SQLException En cas d'erreur lors de l'accès aux données dans le ResultSet.
     */
    private Pictures mapPictures(ResultSet res) throws SQLException {
        Pictures pictures = new Pictures();

        pictures.setId(res.getInt("Id"));
        pictures.setLarge(res.getString("Large"));
        pictures.setMedium(res.getString("medium"));
        pictures.setThumbnail(res.getString("thumbnail"));

        return pictures;
    }


    /**
     * Mappe les données d'un ResultSet à un objet Coords.
     *
     * @param res Le ResultSet contenant les données de la coordonnée.
     * @return Un objet Coords contenant les données mappées.
     * @throws SQLException En cas d'erreur lors de l'accès aux données dans le ResultSet.
     */
    private Coords mapCoords(ResultSet res) throws SQLException {
        Coords coords = new Coords();

        coords.setId(res.getInt("Id"));
        coords.setLatitude(res.getFloat("Latitude"));
        coords.setLongitude(res.getFloat("Longitude"));

        return coords;
    }


    /**
     * Mappe les données d'un ResultSet à un objet {@link Address}.
     *
     * @param res Le ResultSet contenant les données de l'adresse.
     * @return Un objet {@link Address} contenant les données mappées.
     * @throws SQLException En cas d'erreur lors de l'accès aux données dans le ResultSet.
     */
    private Address mapAddress(ResultSet res) throws SQLException {
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


    /**
     * Récupère les informations d'une image {@link Pictures} en fonction de son identifiant (Id).
     *
     * @param idPictures L'identifiant de l'image à récupérer.
     * @return L'objet {@link Pictures} contenant les informations de l'image correspondant à l'identifiant.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données ou si aucune image n'est trouvée.
     */
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


    /**
     * Récupère les informations de coordonnées {@link Coords} en fonction de leur identifiant (Id).
     *
     * @param idCoords L'identifiant des coordonnées à récupérer.
     * @return L'objet {@link Coords} contenant les informations des coordonnées correspondant à l'identifiant.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données ou si aucune coordonnée n'est trouvée.
     */
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

    /**
     * Récupère les informations d'une adresse {@link Address} en fonction de son identifiant (Id).
     *
     * @param idAddress L'identifiant de l'adresse à récupérer.
     * @return L'objet {@link Address} contenant les informations de l'adresse correspondant à l'identifiant.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données ou si aucune adresse n'est trouvée.
     * @see #mapAddress(ResultSet)
     */
    public Address getAddressById(int idAddress) throws SQLException {
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


    /**
     * Récupère les informations complètes d'une coordonnée {@link Coords} en fonction de ses valeurs de latitude et de longitude.
     *
     * @param coords L'objet {@link Coords} contenant les valeurs de latitude et de longitude à rechercher.
     * @return L'objet {@link Coords} contenant les informations complètes de la coordonnée correspondant aux valeurs de latitude et de longitude spécifiées.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données ou si aucune coordonnée correspondante n'est trouvée.
     * @see #mapCoords(ResultSet)
     */
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


    /**
     * Récupère les informations complètes d'une image {@link Pictures} en fonction de ses chemins d'image Large, Medium et Thumbnail.
     *
     * @param pictures L'objet {@link Pictures} contenant les chemins des différentes tailles de l'image à rechercher.
     * @return L'objet {@link Pictures} contenant les informations complètes de l'image correspondant aux chemins spécifiés.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données ou si aucune image correspondante n'est trouvée.
     * @see #mapPictures(ResultSet)
     */
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
            throw new SQLException("L'image (" + pictures + ") n'existe pas.");

        res.close();
        statement.close();

        return fullDataPictures;
    }

    /**
     * Récupère les informations complètes d'une adresse {@link Address} en fonction de ses attributs, y compris le numéro de rue, le nom de rue, la ville et le pays.
     *
     * @param address L'objet {@link Address} contenant les attributs de l'adresse à rechercher.
     * @return L'objet {@link Address} contenant les informations complètes de l'adresse correspondant aux attributs spécifiés.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données ou si aucune adresse correspondante n'est trouvée.
     * @see #mapAddress(ResultSet)
     */
    public Address getFullAddress(Address address) throws SQLException {
        Address fullDataAddress = null;

        final String query = "SELECT * FROM Address WHERE StreetNumber = ? AND StreetName = ?" +
                " AND City = ? AND State = ? AND Country = ? AND Postcode = ?";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setInt(1, address.getStreetNumber());
        statement.setString(2, address.getStreetName());
        statement.setString(3, address.getCity());
        statement.setString(4, address.getState());
        statement.setString(5, address.getCountry());
        statement.setString(6, address.getPostcode());

        ResultSet res = statement.executeQuery();

        if (res.next())
            fullDataAddress = mapAddress(res);

        if (fullDataAddress == null)
            throw new SQLException("L'adresse (" + address + ") n'existe pas.");

        res.close();
        statement.close();

        return fullDataAddress;
    }


    /**
     * Récupère une liste de personnes de la base de données en fonction du type de personne spécifié.
     *
     * @param typePersonne Le type de personne (nom de la table) à partir duquel récupérer les données.
     * @return Une liste d'objets de type générique T contenant les informations des personnes trouvées dans la base de données.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données ou si aucune personne correspondante n'est trouvée.
     * @see #mapPersonne(String, ResultSet)
     */
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


    /**
     * Mappe un enregistrement de type de personne à un objet de type générique T en fonction du type spécifié.
     *
     * @param typePersonne Le type de personne à mapper (nom de la table).
     * @param resultSet    L'enregistrement de la base de données à mapper.
     * @return Un objet de type générique T contenant les informations de la personne mappée.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     * @throws IllegalArgumentException Si le type de personne spécifié n'est pas reconnu.
     * @see #mapProgrammeur(ResultSet)
     * @see #mapManager(ResultSet)
     */
    private T mapPersonne(String typePersonne, ResultSet resultSet) throws SQLException {
        if ("programmeur".equalsIgnoreCase(typePersonne)) {
            return (T) mapProgrammeur(resultSet);
        } else if ("manager".equalsIgnoreCase(typePersonne)) {
            return (T) mapManager(resultSet);
        } else {
            throw new IllegalArgumentException("Type de personne '" + typePersonne + "' n'est pas reconnu");
        }
    }



    /**
     * Mappe un enregistrement de type "Manager" à un objet de type "Manager".
     *
     * @param res L'enregistrement de la base de données à mapper.
     * @return Un objet de type "Manager" contenant les informations du manager mappé.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     * @see Manager
     * @see Title
     * @see Gender
     * @see Pictures
     * @see Coords
     * @see Address
     * @see Hobbies
     * @see Departments
     */
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


    /**
     * Mappe un enregistrement de type "Programmeur" à un objet de type "Programmeur".
     *
     * @param res L'enregistrement de la base de données à mapper.
     * @return Un objet de type "Programmeur" contenant les informations du programmeur mappé.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     * @see Programmeur
     * @see Title
     * @see Gender
     * @see Address
     * @see Pictures
     * @see Coords
     * @see Manager
     * @see Hobbies
     */
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


    /**
     * Récupère un enregistrement d'une personne de type spécifié par son ID.
     *
     * @param typePersonne Le type de personne à récupérer (par exemple, "Programmeur" ou "Manager").
     * @param id L'ID de la personne à rechercher.
     * @return Un objet de type spécifié contenant les informations de la personne recherchée.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     *
     * @see Programmeur
     * @see Manager
     * @see Title
     * @see Gender
     * @see Address
     * @see Pictures
     * @see Coords
     * @see Hobbies
     * @see Departments
     */
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

        if (personne == null)
            throw new SQLException("Le " + typePersonne + " avec l'id " + id + " n'existe pas");

        return personne;
    }

    /**
     * Récupère un enregistrement de personne du type spécifié par son nom complet.
     *
     * @param typePersonne Le type de personne à récupérer (par exemple, "Programmeur" ou "Manager").
     * @param lastName Le nom de famille de la personne.
     * @param firstName Le prénom de la personne.
     * @return Un objet de type spécifié contenant les informations de la personne recherchée.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     *
     * @see Programmeur
     * @see Manager
     * @see Title
     * @see Gender
     * @see Address
     * @see Pictures
     * @see Coords
     * @see Hobbies
     * @see Departments
     *
     * @see #mapProgrammeur(ResultSet)
     * @see #mapManager(ResultSet)
     */
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


    /**
     * Ajoute un enregistrement de personne du type spécifié à la base de données.
     *
     * @param personne L'objet de type spécifié contenant les informations de la personne à ajouter.
     * @throws SQLException En cas d'erreur lors de l'accès à la base de données.
     *
     * @see Programmeur
     * @see Manager
     */
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


    /**
     * Construit et retourne la requête SQL d'insertion pour ajouter un enregistrement de personne du type spécifié.
     *
     * @param columns      Les colonnes et valeurs de l'enregistrement à insérer.
     * @param typePersonne Le type de personne (nom de la table) dans la base de données.
     * @return La requête SQL d'insertion prête à être exécutée.
     */
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


    /**
     * Ajoute un enregistrement de photos {@link Pictures} dans la base de données.
     *
     * @param pictures {@link Pictures} Les informations sur les photos à ajouter.
     * @throws SQLException Si une erreur SQL survient lors de l'ajout.
     */
    public void addPictures(Pictures pictures) throws SQLException {
        final String query = "INSERT INTO Pictures (Large, Medium, Thumbnail) VALUES (?, ?, ?)";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setString(1, pictures.getLarge());
        statement.setString(2, pictures.getMedium());
        statement.setString(3, pictures.getThumbnail());

        statement.executeUpdate();
        statement.close();
    }



    /**
     * Ajoute des coordonnées géographiques {@link Coords} dans la base de données.
     *
     * @param coords {@link Coords} Les coordonnées à ajouter.
     * @throws SQLException Si une erreur SQL survient lors de l'ajout.
     */
    public void addCoords(Coords coords) throws SQLException {
        final String query = "INSERT INTO Coords (Latitude, Longitude)" +
                " VALUES (?, ?)";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setFloat(1, coords.getLatitude());
        statement.setFloat(2, coords.getLongitude());

        statement.executeUpdate();
        statement.close();
    }


    /**
     * Ajoute une adresse {@link Address} dans la base de données.
     *
     * @param address {@link Address} L'adresse à ajouter.
     * @throws SQLException Si une erreur SQL survient lors de l'ajout.
     */
    public void addAddress(Address address) throws SQLException {
        final String query = "INSERT INTO Address(StreetNumber, StreetName, City, State, Country, Postcode)" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setInt(1, address.getStreetNumber());
        statement.setString(2, address.getStreetName());
        statement.setString(3, address.getCity());
        statement.setString(4, address.getState());
        statement.setString(5, address.getCountry());
        statement.setString(6, address.getPostcode());

        statement.executeUpdate();
        statement.close();
    }


    /**
     * Met à jour le salaire d'une personne de type spécifié dans la base de données.
     *
     * @param typePersonne Le type de personne (par exemple, "programmeur" ou "manager").
     * @param id L'identifiant de la personne à mettre à jour.
     * @param newSalary Le nouveau salaire à assigner.
     * @throws SQLException Si une erreur SQL survient lors de la mise à jour du salaire.
     */
    public void setSalaryById(String typePersonne, int id, float newSalary) throws SQLException {
        this.getById(typePersonne, id);

        final String query = "UPDATE " + typePersonne + " SET Salary = ? WHERE Id = ?";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setDouble(1, newSalary);
        statement.setLong(2, id);

        statement.executeUpdate();

        statement.close();
    }


    /**
     * Récupère le nombre de personnes de type spécifié dans la base de données.
     *
     * @param typePersonne Le type de personne (par exemple, "programmeur" ou "manager").
     * @return Le nombre de personnes de ce type dans la base de données.
     * @throws SQLException Si une erreur SQL survient lors de la récupération du nombre de personnes.
     */
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

    /**
     * Supprime une personne de type spécifié de la base de données en utilisant son ID.
     *
     * @param typePersonne Le type de personne (par exemple, "programmeur" ou "manager").
     * @param id L'ID de la personne à supprimer.
     * @throws SQLException Si une erreur SQL survient lors de la suppression de la personne.
     */
    public void deleteById(String typePersonne, int id) throws SQLException {
        this.getById(typePersonne, id);

        final String query = "DELETE FROM " + typePersonne + " WHERE Id = ?";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.setLong(1, id);
        statement.executeUpdate();

        statement.close();
    }

    /**
     * Supprime toutes les personnes du type spécifié de la base de données.
     *
     * @param typePersonne Le type de personne (par exemple, "programmeur" ou "manager").
     * @throws SQLException Si une erreur SQL survient lors de la suppression des personnes.
     */
    public void deleteAll(String typePersonne) throws SQLException {
        String query = "DELETE FROM " + typePersonne;
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }


    /**
     * Supprime toutes les données utilitaires de la base de données, y compris les images {@link Pictures},
     * les coordonnées {@link Coords}
     * et les adresses {@link Address}
     *
     * @throws SQLException Si une erreur SQL survient lors de la suppression des données utilitaires.
     */
    public void deleteUtils() throws SQLException {
        deletePictures();
        deleteCoords();
        deleteAddress();
    }


    /**
     * Supprime toutes les données relatives aux images {@link Pictures} de la base de données.
     *
     * @throws SQLException Si une erreur SQL survient lors de la suppression des images.
     */
    private void deletePictures() throws SQLException {
        String query = "DELETE FROM Pictures";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }


    /**
     * Supprime toutes les données relatives aux coordonnées {@link Coords} de la base de données.
     *
     * @throws SQLException Si une erreur SQL survient lors de la suppression des coordonnées.
     */
    private void deleteCoords() throws SQLException {
        String query = "DELETE FROM Coords";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }


    /**
     * Supprime toutes les données relatives aux adresses {@link Address} de la base de données.
     *
     * @throws SQLException Si une erreur SQL survient lors de la suppression des adresses.
     */
    private void deleteAddress() throws SQLException {
        String query = "DELETE FROM Address";

        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }


    /**
     * Réinitialise l'index de la table de personnes spécifiée (typePersonne).
     *
     * @param typePersonne Le nom de la table de personnes pour laquelle l'index doit être réinitialisé.
     * @throws SQLException Si une erreur SQL survient lors de la réinitialisation de l'index ou de la suppression des données associées.
     */
    public void resetIndex(String typePersonne) throws SQLException {
        resetIndexPictures();
        resetIndexCoords();
        resetIndexAddress();

        String query = "ALTER TABLE " + typePersonne + " AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Réinitialise l'index de la table Pictures {@link Pictures}.
     *
     * @throws SQLException Si une erreur SQL survient lors de la réinitialisation de l'index ou de la suppression des données de la table Pictures.
     */
    private void resetIndexPictures() throws SQLException {
        String query = "ALTER TABLE Pictures AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Réinitialise l'index de la table Coords {@link Coords}.
     *
     * @throws SQLException Si une erreur SQL survient lors de la réinitialisation de l'index ou de la suppression des données de la table Coords.
     */
    private void resetIndexCoords() throws SQLException {
        String query = "ALTER TABLE Coords AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }

    /**
     * Réinitialise l'index de la table Address {@link Address}.
     *
     * @throws SQLException Si une erreur SQL survient lors de la réinitialisation de l'index ou de la suppression des données de la table Address.
     */
    private void resetIndexAddress() throws SQLException {
        String query = "ALTER TABLE Address AUTO_INCREMENT = 1";
        PreparedStatement statement = this.connexion.getConnexion().prepareStatement(query);

        statement.executeUpdate();
        statement.close();
    }


    //Stats

    /**
     * Récupère une personne du type spécifié (typePersonne) dont le salaire correspond à l'agrégation spécifiée.
     *
     * @param typePersonne Le nom de la table de personnes pour laquelle la recherche doit être effectuée.
     * @param aggregation L'agrégation (par exemple, "MAX" ou "MIN") à utiliser pour la recherche du salaire.
     * @return Une instance de personne du type spécifié dont le salaire correspond à l'agrégation spécifiée.
     * @throws SQLException Si une erreur SQL survient lors de la recherche de la personne.
     */
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

    /**
     * Récupère un map associant l'âge (calculé à partir de l'année de naissance) et le salaire moyen pour le type de personne spécifié.
     *
     * @param typePersonne Le nom de la table de personnes pour laquelle la recherche doit être effectuée.
     * @return Un map associant l'âge et le salaire moyen pour ce type de personne.
     * @throws SQLException Si une erreur SQL survient lors de la recherche des données.
     */
    public Map<Integer, Float> getAvgSalaryByAge(String typePersonne) throws SQLException {
        if (this.getCount(typePersonne) == 0)
            throw new SQLException("Impossible de faire des statistiques avec 0 " +
                    typePersonne + " dans la base de donnée");

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

    /**
     * Récupère un map associant le classement en fonction du salaire pour chaque personne du type spécifié.
     *
     * @param typePersonne Le nom de la table de personnes pour laquelle la recherche doit être effectuée.
     * @return Un map associant le classement en fonction du salaire à chaque personne du type spécifié.
     * @throws SQLException Si une erreur SQL survient lors de la recherche des données.
     */
    public Map<Integer, T> getRankBySalary(String typePersonne) throws SQLException {
        if (this.getCount(typePersonne) == 0)
            throw new SQLException("Impossible de faire des statistiques avec 0 " +
                    typePersonne + " dans la base de donnée");

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

    /**
     * Récupère un map associant des tranches de salaire (par exemple, 1000-1999) au nombre de personnes ayant un salaire dans chaque tranche.
     *
     * @param typePersonne Le nom de la table de personnes pour laquelle la recherche doit être effectuée.
     * @return Un map associant des tranches de salaire et le nombre de personnes dans chaque tranche.
     * @throws SQLException Si une erreur SQL survient lors de la recherche des données.
     */
    public Map<Float, Integer> getSalaryHistogram(String typePersonne) throws SQLException {
        if (this.getCount(typePersonne) == 0)
            throw new SQLException("Impossible de faire des statistiques avec 0 " +
                    typePersonne + " dans la base de donnée");

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


    /**
     * Récupère un map associant le genre (par exemple, "M" ou "F") et le salaire moyen pour le type de personne spécifié.
     *
     * @param typePersonne Le nom de la table de personnes pour laquelle la recherche doit être effectuée.
     * @return Un map associant le genre et le salaire moyen pour ce type de personne.
     * @throws SQLException Si une erreur SQL survient lors de la recherche des données.
     */
    public Map<String, Float> getAverageSalaryByGender(String typePersonne) throws SQLException {
        if (this.getCount(typePersonne) == 0)
            throw new SQLException("Impossible de faire des statistiques avec 0 " +
                    typePersonne + " dans la base de donnée");

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

    /**
     * Ferme la connexion à la base de données. Appeler cette méthode lorsque vous avez terminé d'utiliser la base de données.
     */
    public void exit() {
        this.connexion.close();
    }
}
