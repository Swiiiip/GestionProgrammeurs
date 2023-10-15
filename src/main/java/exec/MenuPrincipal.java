package exec;

import dao.ManagerDAO;
import dao.PersonneDAO;
import dao.ProgrammeurDAO;
import org.jetbrains.annotations.NotNull;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;
import prediction.PredictionModel;
import utils.Departments;
import utils.Gender;
import utils.Hobbies;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuPrincipal {
    private final Scanner scanner;
    private final ProgrammeurDAO programmeurDAO;
    private final ManagerDAO managerDAO;

    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.programmeurDAO = new ProgrammeurDAO();
        this.managerDAO = new ManagerDAO();
        Logger logger = Logger.getLogger("com.github.fommil");

        logger.setLevel(Level.SEVERE);

        menuPrincipal();
    }

    private void menuPrincipal() {
        while (true) {
            System.out.println("\n\n********* Menu Principal *********\n");
            System.out.println("1. Menu Programmeurs");
            System.out.println("2. Menu Managers");
            System.out.println("3. Quitter");

            System.out.print("\nChoisissez une option : ");
            int choix = this.getInt();

            switch (choix) {
                case 1:
                    this.menu(programmeurDAO);
                    break;
                case 2:
                    this.menu(managerDAO);
                    break;
                case 3:
                    this.exit();
                    return;
                default:
                    System.out.println("Option invalide. Veuillez choisir à nouveau.");
            }
        }
    }

    private void menu(PersonneDAO<?> personneDAO) {
        while (true) {
            System.out.println("\n\n********* Menu " + personneDAO.getTypeLabel() + " *********\n");
            System.out.println("99. Prédire le salaire d'un " + personneDAO.getTypeLabel());
            System.out.println("1. Afficher tous les " + personneDAO.getTypeLabel());
            System.out.println("2. Afficher un " + personneDAO.getTypeLabel() + " par ID");
            System.out.println("3. Afficher un " + personneDAO.getTypeLabel() + " par nom complet");
            System.out.println("4. Ajouter un nouveau " + personneDAO.getTypeLabel());
            System.out.println("5. Supprimer un " + personneDAO.getTypeLabel() + " par ID");
            System.out.println("6. Supprimer tous les " + personneDAO.getTypeLabel() + "s");
            System.out.println("7. Mettre à jour le salaire d'un " + personneDAO.getTypeLabel() + " par ID");
            System.out.println("8. Afficher le nombre total de " + personneDAO.getTypeLabel() + "s");
            System.out.println("9. Afficher le " + personneDAO.getTypeLabel() + " avec le salaire le plus élevé");
            System.out.println("10. Afficher le " + personneDAO.getTypeLabel() + " avec le salaire le plus bas");
            System.out.println("11. Afficher la moyenne des salaires des " + personneDAO.getTypeLabel() + "s par âge");
            System.out.println("12. Afficher le classement des " + personneDAO.getTypeLabel() + "s par salaire");
            System.out.println("13. Afficher l'histogramme des salaires des " + personneDAO.getTypeLabel() + "s");
            System.out.println("14. Afficher le salaire moyen des " + personneDAO.getTypeLabel() + "s par genre");
            System.out.println("15. Revenir au menu principal");
            System.out.println("16. Quitter");
            System.out.print("\nChoisissez une option : ");

            int choix = this.getInt();

            switch (choix) {
                case 99:
                    this.predictSalary(personneDAO);
                    break;
                case 1:
                    this.displayAll(personneDAO);
                    break;
                case 2:
                    this.displayById(personneDAO);
                    break;
                case 3:
                    this.displayByFullName(personneDAO);
                    break;
                case 4:
                    this.add(personneDAO);
                    break;
                case 5:
                    this.deleteById(personneDAO);
                    break;
                case 6:
                    this.deleteAll(personneDAO);
                    break;
                case 7:
                    this.setSalaryById(personneDAO);
                    break;
                case 8:
                    this.displayCount(personneDAO);
                    break;
                case 9:
                    this.displayPersonWithMaxSalary(personneDAO);
                    break;
                case 10:
                    this.displayPersonWithMinSalary(personneDAO);
                    break;
                case 11:
                    this.displayAvgSalaryByAge(personneDAO);
                    break;
                case 12:
                    this.displayRankBySalary(personneDAO);
                    break;
                case 13:
                    this.displaySalaryHistogram(personneDAO);
                    break;
                case 14:
                    this.displayAverageSalaryByGender(personneDAO);
                    break;
                case 15:
                    return;
                case 16:
                    personneDAO.exit();
                    this.exit();
                default:
                    System.out.println("Option invalide. Veuillez choisir à nouveau.");
            }
        }
    }

    private void predictSalary(PersonneDAO<?> personneDAO) {
        System.out.print("\nEntrez votre age : ");
        double age = this.getAge();

        System.out.print("\nEntrez votre genre : ");
        Gender gender = this.getGender();

        try {
            PredictionModel<? extends PersonneDAO<? extends Personne>> predictionModel = new PredictionModel<>(personneDAO);
            float predictSalary = predictionModel.predictSalary(age, gender.getGender());
            System.out.println("-> Pour un" + (!gender.isWoman() ? " homme" : "e femme") + " de " + age + " ans, " +
                    "le salaire prédictif est de " + predictSalary + "€");
        } catch (Exception e) {
            System.err.println("Le modèle de prédiction a échoué : " + e.getMessage());
        }

    }

    private void displayAPerson(Personne personne) {
        System.out.println(personne);
        System.out.println("---------------------------");
    }

    private void displayAll(PersonneDAO<?> personneDAO) {
        try {
            List<? extends Personne> personnes = personneDAO.getAll();
            System.out.println("\n" + personnes.size() + " " + personneDAO.getTypeLabel() + (personnes.size() == 1 ? " trouvé :" : "s trouvés :"));
            for (Personne p : personnes) {
                this.displayAPerson(p);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des " + personneDAO.getTypeLabel() + "s : " + e.getMessage());
        }
    }

    private void displayById(PersonneDAO<?> personneDAO) {
        int id;
        do {
            try {
                System.out.print("Entrez l'ID du " + personneDAO.getTypeLabel() + " à afficher : ");
                id = getInt();
                Personne personne = personneDAO.getById(id);
                this.displayAPerson(personne);

            } catch (SQLException e) {
                System.err.println(e.getMessage());
                id = 0;
            }
        } while (id == 0);
    }


    private void displayByFullName(PersonneDAO<?> personneDAO) {
        Personne personne = getPersonneByFullName(personneDAO);
        displayAPerson(personne);
    }

    private List<?> collecterInfosPersonne() {
        List<Object> infosPersonne = new ArrayList<>();
        System.out.print("\nEntrez le titre : ");
        String title = this.getTitle();
        infosPersonne.add(title);

        System.out.print("\nEntrez le nom de famille : ");
        String lastName = scanner.next();
        infosPersonne.add(lastName);

        System.out.print("\nEntrez le prénom : ");
        String firstName = scanner.next();
        infosPersonne.add(firstName);

        System.out.print("\nEntrez le genre : ");
        Gender gender = this.getGender();
        infosPersonne.add(gender);

        scanner.nextLine();
        System.out.print("\nEntrez l'adresse : ");
        String address = scanner.nextLine();
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

    private void addProgrammeur(ProgrammeurDAO programmeurDAO) {
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

    @NotNull
    private Programmeur getProgrammeur(List<?> infosPersonne, String pseudo, Manager manager) throws SQLException {
        Gender gender = (Gender) infosPersonne.get(3);

        return new Programmeur(
                (String) infosPersonne.get(0),
                (String) infosPersonne.get(1),
                (String) infosPersonne.get(2),
                gender,
                !gender.isWoman() ? this.programmeurDAO.getPicturesById(1) : this.programmeurDAO.getPicturesById(2),
                (String) infosPersonne.get(4),
                this.programmeurDAO.getCoordsById(1),
                pseudo,
                manager,
                (Hobbies) infosPersonne.get(5),
                (Integer) infosPersonne.get(6),
                (Float) infosPersonne.get(7),
                (Float) infosPersonne.get(8)
        );
    }

    @NotNull
    private Manager getManager(List<?> infosPersonne, Departments department) throws SQLException {
        Gender gender = (Gender) infosPersonne.get(3);
        return new Manager(
                (String) infosPersonne.get(0),
                (String) infosPersonne.get(1),
                (String) infosPersonne.get(2),
                gender,
                !gender.isWoman() ? this.managerDAO.getPicturesById(1) : this.managerDAO.getPicturesById(2),
                (String) infosPersonne.get(4),
                this.managerDAO.getCoordsById(1),
                (Hobbies) infosPersonne.get(5),
                (Integer) infosPersonne.get(6),
                (Float) infosPersonne.get(7),
                (Float) infosPersonne.get(8),
                department
        );
    }

    private void addManager(ManagerDAO managerDAO) {
        System.out.println("Ajout d'un nouveau Manager :");

        List<?> infosPersonne = collecterInfosPersonne();

        System.out.print("\nEntrez le département : ");
        Departments department = this.getDepartment();

        Manager manager;

        try {
            manager = getManager(infosPersonne, department);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            managerDAO.add(manager);
            System.out.println("Manager ajouté avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du Manager : " + e.getMessage());
        }
    }

    private void add(PersonneDAO<?> personneDAO) {
        switch (personneDAO.getTypeLabel()) {
            case "programmeur":
                addProgrammeur((ProgrammeurDAO) personneDAO);
                break;
            case "manager":
                addManager((ManagerDAO) personneDAO);
                break;
            default:
                System.err.println(personneDAO.getTypeLabel() + " n'est pas reconnu.");
        }
    }

    private void deleteById(PersonneDAO<?> personneDAO) {
        int id = 0;
        do {
            try {
                System.out.print("Entrez l'ID du " + personneDAO.getTypeLabel() + " à supprimer : ");
                id = getInt();
                personneDAO.deleteById(id);
                System.out.println(personneDAO.getTypeLabel() + " supprimé avec succès.");

            } catch (SQLException e) {
                System.err.println("Le " + personneDAO.getTypeLabel() + " avec l'id " + id + " n'existe pas");
                id = 0;
            }
        } while (id == 0);
    }

    private void deleteAll(PersonneDAO<?> personneDAO) {
        try {
            personneDAO.deleteAll();
            System.out.println("Tous les " + personneDAO.getTypeLabel() + "s ont été supprimés avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de tous les " + personneDAO.getTypeLabel() + "s.");
        }
    }

    private void setSalaryById(PersonneDAO<?> personneDAO) {
        int id = 0;
        do {
            try {
                System.out.print("Entrez l'ID du " + personneDAO.getTypeLabel() + " à mettre à jour : ");
                id = getInt();
                personneDAO.getById(id);
            } catch (SQLException e) {
                System.err.println("Le " + personneDAO.getTypeLabel() + " avec l'id " + id + " n'existe pas");
                id = 0;
            }
        } while (id == 0);

        try {
            System.out.print("Entrez le nouveau salaire : ");
            float newSalary = getFloat();
            personneDAO.setSalaryById(id, newSalary);
            System.out.println("Salaire du " + personneDAO.getTypeLabel() + " mis à jour avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du salaire du " + personneDAO.getTypeLabel() + " : " + e.getMessage());
        }

    }

    private void displayCount(PersonneDAO<?> personneDAO) {
        try {
            System.out.println("Nombre total de " + personneDAO.getTypeLabel() + "s : " + personneDAO.getCount());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du nombre total de " + personneDAO.getTypeLabel() + "s : " + e.getMessage());
        }
    }

    private void displayPersonWithMaxSalary(PersonneDAO<?> personneDAO) {
        try {
            System.out.println(personneDAO.getTypeLabel() + " avec le salaire le plus élevé : " + personneDAO.getWithMaxSalary());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du " + personneDAO.getTypeLabel() + " avec le salaire le plus élevé : ");
        }
    }

    private void displayPersonWithMinSalary(PersonneDAO<?> personneDAO) {
        try {
            System.out.println(personneDAO.getTypeLabel() + " avec le salaire le plus bas : " + personneDAO.getWithMinSalary());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du " + personneDAO.getTypeLabel() + " avec le salaire le plus bas : ");
        }
    }

    private void displayAvgSalaryByAge(PersonneDAO<?> personneDAO) {
        try {
            Map<Integer, Float> avgSalaryByAge = personneDAO.getAvgSalaryByAge();
            for (Map.Entry<Integer, Float> entry : avgSalaryByAge.entrySet())
                System.out.println("Age : " + entry.getKey() + ", Salaire moyen : " + entry.getValue());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la moyenne des salaires des " + personneDAO.getTypeLabel() + "s par âge : " + e.getMessage());
        }
    }

    private void displayRankBySalary(PersonneDAO<?> personneDAO) {
        try {
            Map<Integer, ? extends Personne> rankBySalary = personneDAO.getRankBySalary();
            for (Map.Entry<Integer, ? extends Personne> entry : rankBySalary.entrySet())
                System.out.println("Classement : " + entry.getKey() + ", Personne : " + entry.getValue());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du classement des " + personneDAO.getTypeLabel() + "s par salaire : " + e.getMessage());
        }
    }

    private void displaySalaryHistogram(PersonneDAO<?> personneDAO) {
        try {
            Map<Float, Integer> salaryHistogram = personneDAO.getSalaryHistogram();
            for (Map.Entry<Float, Integer> entry : salaryHistogram.entrySet())
                System.out.println("Salaire : " + entry.getKey() + ", Fréquence : " + entry.getValue());
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage de l'histogramme des salaires des " + personneDAO.getTypeLabel() + "s : " + e.getMessage());
        }
    }

    private void displayAverageSalaryByGender(PersonneDAO<?> personneDAO) {
        try {
            Map<String, Float> avgSalaryByGender = personneDAO.getAverageSalaryByGender();
            for (Map.Entry<String, Float> entry : avgSalaryByGender.entrySet())
                System.out.println("Genre : " + entry.getKey() + ", Salaire moyen : " + entry.getValue());
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage du salaire moyen des " + personneDAO.getTypeLabel() + "s par genre : " + e.getMessage());
        }
    }

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

    private Departments getDepartment() {
        while (true) {
            try {
                String input = scanner.next();

                for (Departments department : Departments.values()) {
                    if (department.getDepartment().equalsIgnoreCase(input)) {
                        return department;
                    }
                }

                StringBuilder validDepartments = new StringBuilder("les départements valides : ");
                for (Departments department : Departments.values()) {
                    validDepartments.append(department.getDepartment()).append(", ");
                }

                validDepartments.setLength(validDepartments.length() - 2);
                throw new InputMismatchException("Département invalide. Choisissez parmi " + validDepartments);

            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    private String getTitle() {
        String title = null;
        boolean inputValid = false;
        do {
            try {
                title = scanner.next();

                if (title.equalsIgnoreCase("Mr") || title.equalsIgnoreCase("Mrs") || title.equalsIgnoreCase("Ms"))
                    inputValid = true;
                else
                    throw new InputMismatchException("Le titre de la nouvelle personne doit être : " +
                            "\"Mr\" ou \"Mrs\" ou \"Ms\"");
            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }
        } while (!inputValid);

        return title.substring(0, 1).toUpperCase() + title.substring(1).toLowerCase();
    }

    private double getAge() {
        double age;
        do {
            try {
                age = scanner.nextDouble();
                if (!(0 < age && age < 100))
                    throw new InputMismatchException();

            } catch (InputMismatchException e) {
                System.err.println("L'âge doit être compris entre 1 et 100");
                age = 0.0;
                scanner.nextLine();
            }
        } while (age == 0.0);

        return age;
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

    private void exit() {
        scanner.close();
        System.out.println("Au revoir !");
        System.exit(0);
    }
}
