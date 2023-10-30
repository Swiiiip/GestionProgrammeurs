package data.api.mapping;

import dao.ManagerDAO;
import personnes.Manager;
import personnes.utils.Address;
import personnes.utils.Coords;
import personnes.utils.Pictures;
import utils.Departments;
import utils.Gender;
import utils.Hobbies;
import utils.Title;

import java.time.LocalDate;
import java.util.Random;

/**
 * Cette classe est utilisée pour mapper des données de l'API vers des objets de type {@link Manager}.
 */
public class MapManagerFromAPI extends MapPersonneFromAPI<Manager> {

    private final ManagerDAO managerDAO;

    /**
     * Constructeur de la classe.
     *
     * @param managerDAO Le DAO utilisé pour gérer les objets de type {@link Manager} dans la base de données.
     */
    public MapManagerFromAPI(ManagerDAO managerDAO) {
        super();
        this.managerDAO = managerDAO;
    }

    /**
     * Cette méthode permet de mapper des données de l'API vers un objet de type {@link Manager}.
     *
     * @return L'objet de type {@link Manager} mappé depuis les données de l'API.
     * @throws Exception En cas d'erreur lors de la récupération ou du mapping des données.
     */
    @Override
    public Manager map() throws Exception {
        super.map();

        Pictures pictures = this.api.getPictures();
        Address address = this.api.getAddress();
        Coords coords = new Coords(address.getCity(), address.getCountry());

        managerDAO.addPictures(pictures);
        managerDAO.addCoords(coords);
        managerDAO.addAddress(address);

        pictures = this.managerDAO.getPictures(pictures);
        coords = this.managerDAO.getCoords(coords);
        address = this.managerDAO.getAddress(address);

        Title title = this.api.getTitle();
        String lastName = this.api.getLastName();
        String firstName = this.api.getFirstName();
        Gender gender = this.api.getGender();
        int birthYear = this.api.getBirthYear();

        Hobbies hobby = Hobbies.generateRandomHobby();
        Departments department = Departments.generateRandomDepartment();

        int age = LocalDate.now().getYear() - birthYear;

        float salary = 3000.0f + (age * 150.0f);
        float prime = new Random().nextFloat() * 1000.0f;

        if (gender.isWoman())
            salary *= 0.90f;

        return new Manager(title,
                lastName,
                firstName,
                gender,
                pictures,
                address,
                coords,
                hobby,
                birthYear,
                salary,
                prime,
                department);
    }
}
