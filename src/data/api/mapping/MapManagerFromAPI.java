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

public class MapManagerFromAPI extends MapPersonneFromAPI<Manager> {

    private final ManagerDAO managerDAO;

    public MapManagerFromAPI(ManagerDAO managerDAO) {
        super();
        this.managerDAO = managerDAO;
    }

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
