package data.api.mapping;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;
import personnes.Manager;
import personnes.Programmeur;
import personnes.utils.Address;
import personnes.utils.Coords;
import personnes.utils.Pictures;
import utils.Gender;
import utils.Hobbies;
import utils.Title;
import java.time.LocalDate;
import java.util.Random;

/**
 * Cette classe est utilisée pour mapper des données de l'API vers des objets de type {@link Programmeur}.
 */
public class MapProgrammeurFromAPI extends MapPersonneFromAPI<Programmeur> {

    private final ProgrammeurDAO programmeurDAO;
    private final ManagerDAO managerDAO;
    private final int nbManagers;

    /**
     * Constructeur de la classe.
     *
     * @param nbManagers Le nombre de managers disponibles pour l'attribution.
     * @param programmeurDAO Le DAO utilisé pour gérer les objets de type {@link Programmeur} dans la base de données.
     * @param managerDAO Le DAO utilisé pour gérer les objets de type {@link Manager} dans la base de données.
     */
    public MapProgrammeurFromAPI(int nbManagers, ProgrammeurDAO programmeurDAO, ManagerDAO managerDAO) {
        super();
        this.programmeurDAO = programmeurDAO;
        this.managerDAO = managerDAO;
        this.nbManagers = nbManagers;
    }

    /**
     * Cette méthode permet de mapper des données de l'API vers un objet de type {@link Programmeur}.
     *
     * @return L'objet de type {@link Programmeur} mappé depuis les données de l'API.
     * @throws Exception En cas d'erreur lors de la récupération ou du mapping des données.
     */
    @Override
    public Programmeur map() throws Exception {
        super.map();

        Random random = new Random();

        Pictures pictures = this.api.getPictures();
        Address address = this.api.getAddress();
        Coords coords = new Coords(address.getCity(), address.getCountry());

        this.programmeurDAO.addPictures(pictures);
        this.programmeurDAO.addCoords(coords);
        this.programmeurDAO.addAddress(address);

        pictures = this.programmeurDAO.getPictures(pictures);
        coords = this.programmeurDAO.getCoords(coords);
        address = this.programmeurDAO.getAddress(address);

        Title title = this.api.getTitle();
        String lastName = this.api.getLastName();
        String firstName = this.api.getFirstName();
        Gender gender = this.api.getGender();
        Hobbies hobby = Hobbies.generateRandomHobby();
        Manager manager = managerDAO.getById(random.nextInt(this.nbManagers) + 1);
        String pseudo = this.api.getPseudo();

        int birthYear = this.api.getBirthYear();
        int age = LocalDate.now().getYear() - birthYear;
        float salary = 2000.0f + (age * 75.0f);
        float prime = random.nextFloat() * 500.0f;

        if (gender.isWoman())
            salary *= 0.90f;

        return new Programmeur(title,
                lastName,
                firstName,
                gender,
                pictures,
                address,
                coords,
                pseudo,
                manager,
                hobby,
                birthYear,
                salary,
                prime);
    }
}
