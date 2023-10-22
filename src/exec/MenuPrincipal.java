package exec;

import dao.ManagerDAO;
import dao.PersonneDAO;
import dao.ProgrammeurDAO;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;
import personnes.utils.Address;
import personnes.utils.Coords;
import utils.Gender;
import utils.Hobbies;
import utils.Title;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class MenuPrincipal {
    private final Scanner scanner;
    private final ProgrammeurDAO programmeurDAO;
    private final ManagerDAO managerDAO;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.programmeurDAO = new ProgrammeurDAO();
        this.managerDAO = new ManagerDAO();

        start();
    }

    private void start() {
        while (true) {
            this.displayMenu();
            int choix = this.getInt();

            switch (choix) {
                case 1:
                    this.displayAll();
                    break;
                case 2:
                    this.displayById();
                    break;
                case 3:
                    this.deleteById();
                    break;
                case 4:
                    this.addProgrammeur();
                    break;
                case 5:
                    this.setSalaryById();
                    break;
                case 6:
                    this.exit();
                    return;
                default:
                    System.out.println("Option invalide. Veuillez choisir à nouveau.");
            }
        }
    }

    private void displayMenu(){
        System.out.println("\n\n<<<<<<<<<<<<  MENU  >>>>>>>>>>>>\n");
        System.out.println("1. Afficher tous les programmeurs");
        System.out.println("2. Afficher un programmeur");
        System.out.println("3. Supprimer un programmeur");
        System.out.println("4. Ajouter un programmeur");
        System.out.println("5. Modifier le salaire");
        System.out.println("6. Quitter le programme\n");
        System.out.print("Quel est votre choix ? : ");
    }

    private void displayAll() {
        try {
            List<Programmeur> programmeurs = programmeurDAO.getAll();
            System.out.println("\n" + programmeurs.size() + " " + programmeurDAO.getTypeLabel() + (programmeurs.size() == 1 ? " trouvé :" : "s trouvés :"));
            for (Programmeur p : programmeurs) {
                this.displayAPerson(p);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des " + programmeurDAO.getTypeLabel() + "s : " + e.getMessage());
        }
    }

    private void displayById() {
        int id;
        do {
            try {
                System.out.print("Entrez l'ID du " + programmeurDAO.getTypeLabel() + " à afficher : ");
                id = getInt();
                Programmeur prog = programmeurDAO.getById(id);
                this.displayAPerson(prog);

            } catch (SQLException e) {
                System.err.println(e.getMessage());
                id = 0;
            }
        } while (id == 0);
    }

    private void displayAPerson(Programmeur prog) {
        System.out.println(prog);
        System.out.println("---------------------------");
    }
    private void addProgrammeur() {
        System.out.println("Ajout d'un nouveau Programmeur :");

        List<?> infosPersonne = collecterInfosPersonne();

        System.out.print("\nEntrez le pseudo : ");
        String pseudo = scanner.next();
        Manager manager = (Manager) this.getPersonneByFullName(managerDAO);
        Programmeur prog;

        try {
            prog = getProgrammeur(infosPersonne, pseudo, manager);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            programmeurDAO.add(prog);
            System.out.println("Programmeur ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du Programmeur : " + e.getMessage());
        }
    }
    private List<?> collecterInfosPersonne() {
        List<Object> infosPersonne = new ArrayList<>();
        System.out.print("\nEntrez le titre : ");
        Title title = this.getTitle();
        infosPersonne.add(title);

        System.out.print("\nEntrez le nom de famille : ");
        String lastName = this.getName();
        infosPersonne.add(lastName);

        System.out.print("\nEntrez le prénom : ");
        String firstName = this.getName();
        infosPersonne.add(firstName);

        System.out.print("\nEntrez le genre : ");
        Gender gender = this.getGender();
        infosPersonne.add(gender);

        scanner.nextLine();
        System.out.println("\nEntrez votre Adresse : ");
        Address address = this.getAddress();
        infosPersonne.add(address);

        System.out.print("\nEntrez le hobby : ");
        Hobbies hobby = this.getHobby();
        infosPersonne.add(hobby);

        System.out.print("\nEntrez l'année de naissance : ");
        int birthYear = getDoB();
        infosPersonne.add(birthYear);

        System.out.print("\nEntrez le salaire : ");
        float salary = getFloat();
        infosPersonne.add(salary);

        System.out.print("\nEntrez la prime : ");
        float prime = getFloat();
        infosPersonne.add(prime);

        try {
            Coords coords = new Coords(address.getCity(), address.getCountry());
            this.programmeurDAO.addCoords(coords);
            coords = this.programmeurDAO.getCoords(coords);

            infosPersonne.add(coords);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return infosPersonne;
    }

    private Personne getPersonneByFullName(PersonneDAO<?> personneDAO) {
        String fullName;
        Personne personne = null;
        scanner.nextLine();
        do {
            System.out.print("Entrez le nom complet : ");
            fullName = scanner.nextLine();

            String[] names = fullName.split(" ");

            try {
                if (names.length != 2)
                    throw new InputMismatchException("Vous devez renseigner : Nom Prénom");
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
                fullName = "invalid";
                scanner.nextLine();
            }

            if (!fullName.equals("invalid")) {
                String lastName = names[0];
                String firstName = names[1];
                try {
                    personne = personneDAO.getByFullName(lastName, firstName);
                } catch (SQLException e) {
                    System.err.println(e.getMessage());
                    scanner.nextLine();
                }
            }
        } while (personne == null);

        return personne;
    }

    private Programmeur getProgrammeur(List<?> infosPersonne, String pseudo, Manager manager) throws SQLException {
        Gender gender = (Gender) infosPersonne.get(3);

        return new Programmeur(
                (Title) infosPersonne.get(0),
                (String) infosPersonne.get(1),
                (String) infosPersonne.get(2),
                gender,
                !gender.isWoman() ? this.programmeurDAO.getPicturesById(1) : this.programmeurDAO.getPicturesById(2),
                (Address) infosPersonne.get(4),
                (Coords) infosPersonne.get(9),
                pseudo,
                manager,
                (Hobbies) infosPersonne.get(5),
                (Integer) infosPersonne.get(6),
                (Float) infosPersonne.get(7),
                (Float) infosPersonne.get(8)
        );
    }

    private int isReal(){
        int id = 0;
        do {
            try {
                System.out.print("Entrez l'ID du " + programmeurDAO.getTypeLabel() + " à supprimer : ");
                id = getInt();
                programmeurDAO.getById(id);
            } catch (SQLException e) {
                System.err.println("Le " + programmeurDAO.getTypeLabel() + " avec l'id " + id + " n'existe pas");
                id = 0;
            }
        } while (id == 0);

        return id;
    }
    private void deleteById() {
        int id = isReal();

        try {
            programmeurDAO.deleteById(id);
            System.out.println(programmeurDAO.getTypeLabel() + " supprimé avec succès!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void setSalaryById() {
        int id = isReal();

        try {
            System.out.print("Entrez le nouveau salaire : ");
            float newSalary = getFloat();
            programmeurDAO.setSalaryById(id, newSalary);
            System.out.println("Salaire du " + programmeurDAO.getTypeLabel() + " mis à jour avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du salaire du " + programmeurDAO.getTypeLabel() + " : " + e.getMessage());
        }

    }

    private void exit() {
        scanner.close();
        System.out.println("Au revoir !");
        System.exit(0);
    }

    /* UTILS */

    private int getInt() {
        int input = 0;
        boolean inputValid = false;

        do {
            try {
                input = scanner.nextInt();

                if (input >= 1) {
                    inputValid = true;
                } else {
                    System.err.println("Le choix doit être strictement positif. Veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrez un nombre entier valide.");
                scanner.nextLine();
            }
        } while (!inputValid);

        return input;
    }


    private float getFloat() {
        float input = 0;
        boolean inputValid = false;

        do {
            try {
                input = scanner.nextFloat();

                if (input >= 1) {
                    inputValid = true;
                } else {
                    System.err.println("Le choix doit être strictement positif. Veuillez réessayer.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Entrez un nombre réel valide. (2323,55)");
                scanner.nextLine();
            }
        } while (!inputValid);

        return input;
    }

    private String getName(){
        String name = scanner.next();

        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    private Gender getGender() {
        while (true) {
            try {
                String input = scanner.next();

                for (Gender gender : Gender.values()) {
                    if (gender.getGender().equalsIgnoreCase(input)) {
                        return gender;
                    }
                }

                StringBuilder validGender = new StringBuilder("les genres valides : ");
                for (Gender gender : Gender.values()) {
                    validGender.append(gender.getGender()).append(", ");
                }

                validGender.setLength(validGender.length() - 2);
                throw new InputMismatchException("Genre invalide. Choisissez parmi " + validGender);

            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    private Address getAddress() {
        System.out.print("Entrez votre numéro de rue : ");
        int streetNum = getInt();

        scanner.nextLine();
        System.out.print("Entrez votre nom de rue : ");
        String streetName = scanner.nextLine();

        System.out.print("Entrez votre ville : ");
        String city = scanner.nextLine();

        System.out.print("Entrez votre région : ");
        String state = scanner.nextLine();

        System.out.print("Entrez votre pays : ");
        String country = scanner.nextLine();

        System.out.print("Entrez votre code postale : ");
        String postcode = scanner.next();

        Address address = new Address(streetNum, streetName, city, state, country, postcode);

        try{
            programmeurDAO.addAddress(address);
            address = programmeurDAO.getAddress(address);
        } catch (Exception e){
            System.err.println("L'ajout de l'adresse a échouée.");
        }

        return address;
    }



    private Hobbies getHobby() {
        while (true) {
            try {
                String input = scanner.next();

                for (Hobbies hobby : Hobbies.values()) {
                    if (hobby.getHobby().equalsIgnoreCase(input)) {
                        return hobby;
                    }
                }

                StringBuilder validHobbies = new StringBuilder("les hobbies valides : ");
                for (Hobbies hobby : Hobbies.values()) {
                    validHobbies.append(hobby.getHobby()).append(", ");
                }

                validHobbies.setLength(validHobbies.length() - 2);
                throw new InputMismatchException("Hobby invalide. Choisissez parmi " + validHobbies);

            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    private Title getTitle() {
        while (true) {
            try {
                String input = scanner.next();

                for (Title title : Title.values()) {
                    if (title.getTitle().equalsIgnoreCase(input)) {
                        return title;
                    }
                }

                StringBuilder validTitle = new StringBuilder("les titres valides : ");
                for (Title title : Title.values()) {
                    validTitle.append(title.getTitle()).append(", ");
                }

                validTitle.setLength(validTitle.length() - 2);
                throw new InputMismatchException("Titre invalide. Choisissez parmi " + validTitle);

            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }

        }
    }
    private int getDoB() {
        int birthYear;
        do {
            int currentYear = LocalDate.now().getYear();
            try {
                birthYear = scanner.nextInt();
                if (!(currentYear > birthYear && birthYear > currentYear - 100))
                    throw new InputMismatchException();

            } catch (InputMismatchException e) {
                System.err.println("L'année de naissance doit être comprise entre " +
                        (currentYear - 100) + " et " + currentYear);
                birthYear = 0;
                scanner.nextLine();
            }
        } while (birthYear == 0);

        return birthYear;
    }
}
