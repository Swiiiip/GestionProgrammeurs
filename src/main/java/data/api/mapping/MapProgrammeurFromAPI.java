package data.api.mapping;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;
import personnes.Manager;
import personnes.Programmeur;
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

        Pictures pictures = this.api.parsePicturesFromJson();
        Coords coords = this.api.parseCoordsFromJson();

        this.programmeurDAO.addPictures(pictures);
        this.programmeurDAO.addCoords(coords);

        pictures = this.programmeurDAO.getPictures(pictures);
        coords = this.programmeurDAO.getCoords(coords);

        Title title = this.api.parseTitleFromJson();
        String lastName = this.api.parseLastNameFromJson();
        String firstName = this.api.parseFirstNameFromJson();
        Gender gender = this.api.parseGenderFromJson();
        String address = this.api.parseAddressFromJson();
        Hobbies hobby = Hobbies.generateRandomHobby();
        Manager manager = managerDAO.getById(random.nextInt(this.nbManagers) + 1);
        String pseudo = this.api.parsePseudoFromJson();

        int birthYear = this.api.parseBirthYearFromJson();
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
