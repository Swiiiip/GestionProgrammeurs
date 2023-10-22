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

public class MapProgrammeurFromAPI extends MapPersonneFromAPI<Programmeur> {

    private final ProgrammeurDAO programmeurDAO;

    private final ManagerDAO managerDAO;

    private final int nbManagers;

    public MapProgrammeurFromAPI(int nbManagers, ProgrammeurDAO programmeurDAO, ManagerDAO managerDAO) {
        super();
        this.programmeurDAO = programmeurDAO;
        this.managerDAO = managerDAO;
        this.nbManagers = nbManagers;
    }

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
