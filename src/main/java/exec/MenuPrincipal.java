package exec;

import dao.ManagerDAO;
import dao.PersonneDAO;
import dao.ProgrammeurDAO;
import data.export.CSVExporter;
import data.export.PDFExporter;
import org.jetbrains.annotations.NotNull;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;
import personnes.utils.Address;
import personnes.utils.Coords;
import prediction.PredictionModel;
import utils.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La classe MenuPrincipal représente le menu principal de l'application de gestion de type de personne,
 * ici [{@link Programmeur}, {@link Manager}]
 * Elle permet aux utilisateurs d'accéder à diverses fonctionnalités pour interagir avec les données du type de personne spécifiée.
 * Les actions disponibles incluent l'affichage des données, l'ajout, la suppression et la mise à jour des informations des individus.
 * Elle offre la possibilité d'afficher des statistiques, ainsi que d'afficher la prédiction de salaire pour un âge et sexe donné.
 * Tous les affichages se font dans la console.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class MenuPrincipal {
    private final Scanner scanner;
    private final ProgrammeurDAO programmeurDAO;
    private final ManagerDAO managerDAO;

    /**
     * Constructeur de la classe MenuPrincipal. Il initialise les composants essentiels de l'application, tels que le scanner
     * pour la saisie utilisateur, les objets `ProgrammeurDAO` et `ManagerDAO` pour la gestion des données, et configure le
     * logger pour afficher uniquement les messages de niveau `SEVERE`.
     */
    public MenuPrincipal() {
        this.scanner = new Scanner(System.in);
        this.programmeurDAO = new ProgrammeurDAO();
        this.managerDAO = new ManagerDAO();
        Logger logger = Logger.getLogger("com.github.fommil");

        logger.setLevel(Level.SEVERE);

        menuPrincipal();
    }

    /**
     * Méthode principale du menu. Affiche le menu principal de l'application, permet à l'utilisateur de faire un choix,
     * puis exécute l'action correspondante en fonction de ce choix.
     */
    public void menuPrincipal() {
        while (true) {
            displayMainMenu();

            int choix = getInt();

            Map<Integer, Consumer<PersonneDAO<?>>> menuActions = new HashMap<>();
            menuActions.put(1, this::menuProgrammeurs);
            menuActions.put(2, this::menuManagers);
            menuActions.put(3, this::exit);

            Consumer<PersonneDAO<?>> action = menuActions.get(choix);
            if (action != null) {
                action.accept(null);
            } else {
                System.out.println("Option invalide. Veuillez choisir à nouveau.");
            }
        }
    }

    /**
     * Affiche le menu principal de l'application, listant les différentes options disponibles.
     */
    private void displayMainMenu() {
        System.out.println("\n\n********* Menu Principal *********\n");
        System.out.println("1. Menu Programmeurs");
        System.out.println("2. Menu Managers");
        System.out.println("3. Quitter");
        System.out.print("\nChoisissez une option : ");
    }

    /**
     * Affiche le menu pour la gestion des Programmeurs.
     *
     * @param personneDAO Le DAO correspondant aux Programmeurs.
     */
    private void menuProgrammeurs(PersonneDAO<?> personneDAO) {
        menu(programmeurDAO);
    }

    /**
     * Affiche le menu pour la gestion des Managers.
     *
     * @param personneDAO Le DAO correspondant aux Managers.
     */
    private void menuManagers(PersonneDAO<?> personneDAO) {
        menu(managerDAO);
    }

    /**
     * Affiche un sous-menu pour la gestion des personnes (Programmeurs ou Managers).
     *
     * @param personneDAO Le DAO correspondant aux personnes.
     */
    private void menu(PersonneDAO<?> personneDAO) {
        while (true) {
            displaySubMenu(personneDAO.getTypeLabel());
            int choix = getInt();

            Map<Integer, Consumer<PersonneDAO<?>>> submenuActions = new HashMap<>();
            submenuActions.put(99, this::predictSalary);
            submenuActions.put(98, this::exportData);
            submenuActions.put(1, this::displayAll);
            submenuActions.put(2, this::displayById);
            submenuActions.put(3, this::displayByFullName);
            submenuActions.put(4, this::add);
            submenuActions.put(5, this::deleteById);
            submenuActions.put(6, this::deleteAll);
            submenuActions.put(7, this::setSalaryById);
            submenuActions.put(8, this::displayCount);
            submenuActions.put(9, this::displayPersonWithMaxSalary);
            submenuActions.put(10, this::displayPersonWithMinSalary);
            submenuActions.put(11, this::displayAvgSalaryByAge);
            submenuActions.put(12, this::displayRankBySalary);
            submenuActions.put(13, this::displaySalaryHistogram);
            submenuActions.put(14, this::displayAverageSalaryByGender);
            submenuActions.put(15, x -> {
                System.out.println("Retour au menu principal...");
                throw new ReturnToMainMenuException();
            });
            submenuActions.put(16, this::exit);

            Consumer<PersonneDAO<?>> action = submenuActions.get(choix);
            if (action != null)
                try {
                    action.accept(personneDAO);
                } catch (ReturnToMainMenuException e) {
                    break;
                }
            else
                System.out.println("Option invalide. Veuillez choisir à nouveau.");

        }
    }

    /**
     * Exporte les données des personnes au format CSV ou PDF.
     * Cette méthode récupère les données des personnes à partir de l'objet PersonneDAO spécifié,
     * puis propose à l'utilisateur de choisir le format d'export (CSV ou PDF). Les données
     * sont ensuite exportées dans le format choisi.
     * @param personneDAO L'objet PersonneDAO qui permet d'accéder aux données des personnes.
     */
    private void exportData(PersonneDAO<?> personneDAO) {
        List<? extends Personne> personnes;
        try {
            personnes = personneDAO.getAll();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return;
        }

        List<Map<String, Object>> data = new ArrayList<>();

        for (Personne personne : personnes)
            data.add(personne.getColumns());

        boolean formatChoisi = false;
        String input = null;
        System.out.print("Choisissez le format (CSV ou PDF) : ");

        while(!formatChoisi) {
            try {
            input = scanner.next().toLowerCase();

            switch (input){
                case "csv":
                    new CSVExporter(data).export(personneDAO.getTypeLabel() + 's');
                    formatChoisi = true;
                    break;

                case "pdf":
                    new PDFExporter(data).export(personneDAO.getTypeLabel() + 's');
                    formatChoisi = true;
                    break;

                default:
                    throw new InputMismatchException(input);

            }

            } catch (InputMismatchException e){
                System.err.println("Le format choisi '" + e.getMessage() + "' ne correspond pas. Veuillez réessayer." +
                        "\nChoix possibles : CSV ou PDF.");
            }
        }
        
        System.out.println("\nLes données des " + personneDAO.getTypeLabel() + "s ont été téléchargées au format "
                + input + " dans votre dossier 'Downloads'.");
    }

    /**
     * Affiche le sous-menu pour la gestion des personnes, telles que Programmeurs ou Managers.
     *
     * @param typeLabel Le libellé du type de personne (Programmeurs ou Managers).
     */
    private void displaySubMenu(String typeLabel) {
        System.out.println("\n\n********* Menu " + typeLabel + " *********\n");
        System.out.println("99. Prédire le salaire d'un " + typeLabel);
        System.out.println("98. Exportez les données des " + typeLabel + "s");
        System.out.println("1. Afficher tous les " + typeLabel);
        System.out.println("2. Afficher un " + typeLabel + " par ID");
        System.out.println("3. Afficher un " + typeLabel + " par nom complet");
        System.out.println("4. Ajouter un nouveau " + typeLabel);
        System.out.println("5. Supprimer un " + typeLabel + " par ID");
        System.out.println("6. Supprimer tous les " + typeLabel + "s");
        System.out.println("7. Mettre à jour le salaire d'un " + typeLabel + " par ID");
        System.out.println("8. Afficher le nombre total de " + typeLabel + "s");
        System.out.println("9. Afficher le " + typeLabel + " avec le salaire le plus élevé");
        System.out.println("10. Afficher le " + typeLabel + " avec le salaire le plus bas");
        System.out.println("11. Afficher la moyenne des salaires des " + typeLabel + "s par âge");
        System.out.println("12. Afficher le classement des " + typeLabel + "s par salaire");
        System.out.println("13. Afficher l'histogramme des salaires des " + typeLabel + "s");
        System.out.println("14. Afficher le salaire moyen des " + typeLabel + "s par genre");
        System.out.println("15. Revenir au menu principal");
        System.out.println("16. Quitter");
        System.out.print("\nChoisissez une option : ");
    }

    /**
     * Prédit le salaire d'une personne en fonction de son âge et de son genre.
     *
     * @param personneDAO Le DAO correspondant à la personne (Programmeur ou Manager).
     */
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

    /**
     * Affiche les détails d'une personne.
     *
     * @param personne La personne à afficher.
     */
    private void displayAPerson(Personne personne) {
        System.out.println(personne);
        System.out.println("---------------------------");
    }

    /**
     * Affiche tous les Programmeurs ou Managers.
     *
     * @param personneDAO Le DAO correspondant aux personnes.
     */
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


    /**
     * Affiche une personne par son ID.
     *
     * @param personneDAO Le DAO correspondant à la personne (Programmeur ou Manager).
     */
    private void displayById(PersonneDAO<?> personneDAO) {
        try {
            personneDAO.getAll();
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return;
        }
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


    /**
     * Affiche une personne par son nom complet.
     *
     * @param personneDAO Le DAO correspondant à la personne (Programmeur ou Manager).
     */
    private void displayByFullName(PersonneDAO<?> personneDAO) {
        try {
            personneDAO.getAll();
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return;
        }
        Personne personne = getPersonneByFullName(personneDAO);
        displayAPerson(personne);
    }

    /**
     * Méthode pour collecter les informations communes à une personne.
     * Demande à l'utilisateur de fournir diverses informations sur la personne.
     * @return Une liste d'informations sur la personne.
     */
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

    /**
     * Méthode pour obtenir une personne en recherchant par nom complet.
     * Demande à l'utilisateur de saisir le nom complet de la personne.
     * @param personneDAO L'objet DAO associé.
     * @return La personne correspondant au nom complet saisi.
     */
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

    /**
     * Méthode pour ajouter un nouveau Programmeur.
     * Demande à l'utilisateur de fournir des informations et crée un objet {@link Programmeur}.
     */
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

    /**
     * Méthode pour créer un objet {@link Programmeur} à partir des informations saisies.
     * @param infosPersonne Les informations personnelles de la personne.
     * @param pseudo Le pseudo du Programmeur.
     * @param manager Le Manager associé au Programmeur.
     * @return Un objet {@link Programmeur} créé avec les informations fournies.
     * @throws SQLException En cas d'erreur liée à la base de données.
     */
    @NotNull
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

    /**
     * Méthode pour créer un objet {@link Manager} à partir des informations saisies.
     * @param infosPersonne Les informations personnelles de la personne.
     * @param department Le département du Manager.
     * @return Un objet {@link Manager} créé avec les informations fournies.
     * @throws SQLException En cas d'erreur liée à la base de données.
     */
    @NotNull
    private Manager getManager(List<?> infosPersonne, Departments department) throws SQLException {
        Gender gender = (Gender) infosPersonne.get(3);
        return new Manager(
                (Title) infosPersonne.get(0),
                (String) infosPersonne.get(1),
                (String) infosPersonne.get(2),
                gender,
                !gender.isWoman() ? this.managerDAO.getPicturesById(1) : this.managerDAO.getPicturesById(2),
                (Address) infosPersonne.get(4),
                (Coords) infosPersonne.get(9),
                (Hobbies) infosPersonne.get(5),
                (Integer) infosPersonne.get(6),
                (Float) infosPersonne.get(7),
                (Float) infosPersonne.get(8),
                department
        );
    }

    /**
     * Méthode pour ajouter un nouveau Manager.
     * Demande à l'utilisateur de fournir des informations et crée un objet {@link Manager}.
     */
    private void addManager() {
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

    /**
     * Méthode pour ajouter une personne en fonction du type défini par le DAO.
     * Cette méthode utilise un DAO pour déterminer le type de personne à ajouter.
     * Appelle ensuite la méthode appropriée ({@link #addProgrammeur} ou {@link #addManager}) en fonction du type.
     * @param personneDAO Le DAO spécifiant le type de personne à ajouter.
     */
    private void add(PersonneDAO<?> personneDAO) {
        switch (personneDAO.getTypeLabel()) {
            case "programmeur":
                addProgrammeur();
                break;
            case "manager":
                addManager();
                break;
            default:
                System.err.println(personneDAO.getTypeLabel() + " n'est pas reconnu. Veuillez réessayer.");
        }
    }

    /**
     * Méthode pour vérifier si un ID correspond à une personne existante dans la base de données.
     * @return L'ID d'une personne existante.
     */
    private int isReal(){
        int id;
        do {
            try {
                id = getInt();
                programmeurDAO.getById(id);
            } catch (SQLException e) {
                System.err.println(e.getMessage() + ". Veuillez réessayer.");
                id = 0;
            }
        } while (id == 0);

        return id;
    }

    /**
     * Méthode pour supprimer une personne par ID en utilisant un DAO spécifié.
     * Demande à l'utilisateur l'ID de la personne à supprimer et effectue la suppression.
     * @param personneDAO Le DAO spécifiant le type de personne à supprimer.
     */
    private void deleteById(PersonneDAO<?> personneDAO) {
        try {
            personneDAO.getAll();
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return;
        }
        System.out.print("Entrez l'ID du " + programmeurDAO.getTypeLabel() + " à supprimer : ");
        int id = isReal();
        try {
            personneDAO.deleteById(id);
            System.out.println(personneDAO.getTypeLabel() + " supprimé avec succès!");
        } catch (SQLException e) {
            System.err.println(e.getMessage() + ". Veuillez réessayer.");
        }
    }

    /**
     * Méthode pour mettre à jour le salaire d'une personne par ID en utilisant un DAO spécifié.
     * Demande à l'utilisateur l'ID de la personne et le nouveau salaire, puis effectue la mise à jour.
     * @param personneDAO Le DAO spécifiant le type de personne à mettre à jour.
     */
    private void setSalaryById(PersonneDAO<?> personneDAO) {
        try {
            personneDAO.getAll();
        } catch (SQLException e){
            System.err.println(e.getMessage());
            return;
        }
        System.out.print("Entrez l'ID du " + programmeurDAO.getTypeLabel() + " à mettre à jour le salaire : ");
        int id = isReal();
        try {
            System.out.print("Entrez le nouveau salaire : ");
            float newSalary = getFloat();
            personneDAO.setSalaryById(id, newSalary);
            System.out.println("Salaire du " + personneDAO.getTypeLabel() + " mis à jour avec succès.");

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du salaire du " + personneDAO.getTypeLabel()
                    + " : " + e.getMessage() + ". Veuillez réessayer.");
        }

    }

    /**
     * Méthode pour supprimer toutes les personnes en utilisant un DAO spécifié.
     * @param personneDAO Le DAO spécifiant le type de personne à supprimer.
     */
    private void deleteAll(PersonneDAO<?> personneDAO) {
        try {
            personneDAO.deleteAll();
            System.out.println("Tous les " + personneDAO.getTypeLabel() + "s ont été supprimés avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de tous les " + personneDAO.getTypeLabel() + "s.");
        }
    }

    /**
     * Méthode pour afficher le nombre total de personnes d'un type spécifique.
     * @param personneDAO Le DAO spécifiant le type de personne.
     */
    private void displayCount(PersonneDAO<?> personneDAO) {
        try {
            System.out.println("Nombre total de " + personneDAO.getTypeLabel() + "s : " + personneDAO.getCount());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du nombre total de " + personneDAO.getTypeLabel() + "s : " + e.getMessage());
        }
    }

    /**
     * Méthode pour afficher la personne avec le salaire le plus élevé d'un type spécifique.
     * @param personneDAO Le DAO spécifiant le type de personne.
     */
    private void displayPersonWithMaxSalary(PersonneDAO<?> personneDAO) {
        try {
            System.out.println(personneDAO.getTypeLabel() + " avec le salaire le plus élevé : " + personneDAO.getWithMaxSalary());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du " + personneDAO.getTypeLabel() + " avec le salaire le plus élevé : ");
        }
    }

    /**
     * Méthode pour afficher la personne avec le salaire le plus bas d'un type spécifique.
     * @param personneDAO Le DAO spécifiant le type de personne.
     */
    private void displayPersonWithMinSalary(PersonneDAO<?> personneDAO) {
        try {
            System.out.println(personneDAO.getTypeLabel() + " avec le salaire le plus bas : " + personneDAO.getWithMinSalary());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du " + personneDAO.getTypeLabel() + " avec le salaire le plus bas : ");
        }
    }

    /**
     * Méthode pour afficher la moyenne des salaires par âge pour un type de personne spécifique.
     * @param personneDAO Le DAO spécifiant le type de personne.
     */
    private void displayAvgSalaryByAge(PersonneDAO<?> personneDAO) {
        try {
            Map<Integer, Float> avgSalaryByAge = personneDAO.getAvgSalaryByAge();
            for (Map.Entry<Integer, Float> entry : avgSalaryByAge.entrySet())
                System.out.println("Age : " + entry.getKey() + ", Salaire moyen : " + entry.getValue());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la moyenne des salaires des " + personneDAO.getTypeLabel() + "s par âge : " + e.getMessage());
        }
    }

    /**
     * Méthode pour afficher le classement des personnes par salaire pour un type de personne spécifique.
     * @param personneDAO Le DAO spécifiant le type de personne.
     */
    private void displayRankBySalary(PersonneDAO<?> personneDAO) {
        try {
            Map<Integer, ? extends Personne> rankBySalary = personneDAO.getRankBySalary();
            for (Map.Entry<Integer, ? extends Personne> entry : rankBySalary.entrySet())
                System.out.println("Classement : " + entry.getKey() + ", Personne : " + entry.getValue());
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération du classement des " + personneDAO.getTypeLabel() + "s par salaire : " + e.getMessage());
        }
    }

    /**
     * Méthode pour afficher un histogramme des salaires pour un type de personne spécifique.
     * @param personneDAO Le DAO spécifiant le type de personne.
     */
    private void displaySalaryHistogram(PersonneDAO<?> personneDAO) {
        try {
            Map<Float, Integer> salaryHistogram = personneDAO.getSalaryHistogram();
            for (Map.Entry<Float, Integer> entry : salaryHistogram.entrySet())
                System.out.println("Salaire : " + entry.getKey() + ", Fréquence : " + entry.getValue());
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage de l'histogramme des salaires des " + personneDAO.getTypeLabel() + "s : " + e.getMessage());
        }
    }

    /**
     * Méthode pour afficher le salaire moyen par genre pour un type de personne spécifique.
     * @param personneDAO Le DAO spécifiant le type de personne.
     */
    private void displayAverageSalaryByGender(PersonneDAO<?> personneDAO) {
        try {
            Map<String, Float> avgSalaryByGender = personneDAO.getAverageSalaryByGender();
            for (Map.Entry<String, Float> entry : avgSalaryByGender.entrySet())
                System.out.println("Genre : " + entry.getKey() + ", Salaire moyen : " + entry.getValue());
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage du salaire moyen des " + personneDAO.getTypeLabel() + "s par genre : " + e.getMessage());
        }
    }

    /**
     * Récupère un entier valide depuis la console.
     *
     * @return L'entier positif récupéré depuis l'entrée de l'utilisateur.
     * @throws InputMismatchException Si l'entrée n'est pas un entier valide.
     */
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
                System.err.println("Entrez un nombre entier valide. Veuillez réessayer.\"");
                scanner.nextLine();
            }
        } while (!inputValid);

        return input;
    }


    /**
     * Récupère un nombre réel positif depuis la console.
     *
     * @return Le nombre réel positif récupéré depuis l'entrée de l'utilisateur.
     * @throws InputMismatchException Si l'entrée n'est pas un nombre réel valide.
     */
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
                System.err.println("Entrez un nombre réel valide. (2323,55). Veuillez réessayer.\"");
                scanner.nextLine();
            }
        } while (!inputValid);

        return input;
    }

    /**
     * Récupère un nom depuis la console en format "Nom" (première lettre en majuscule, le reste en minuscules).
     *
     * @return Le nom formaté récupéré depuis l'entrée de l'utilisateur.
     */
    private String getName(){
        String name = scanner.next();

        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    /**
     * Récupère le genre depuis la console en utilisant les valeurs valides de l'énumération Gender.
     *
     * @return Le genre récupéré depuis l'entrée de l'utilisateur.
     * @throws InputMismatchException Si l'entrée n'est pas un genre valide.
     */
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
                throw new InputMismatchException("Genre invalide. Choisissez parmi " + validGender
                        + ". Veuillez réessayer.");

            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    /**
     * Récupère une adresse complète depuis la console en demandant différents détails tels que le numéro de rue,
     * le nom de rue, la ville, etc.
     *
     * @return L'adresse complète récupérée depuis l'entrée de l'utilisateur.
     */
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



    /**
     * Récupère un loisir depuis la console en demandant à l'utilisateur de choisir parmi les loisirs valides.
     *
     * @return Le loisir sélectionné par l'utilisateur.
     */
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
                throw new InputMismatchException("Hobby invalide. Choisissez parmi " + validHobbies
                        + ". Veuillez réessayer.");

            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    /**
     * Récupère un département depuis la console en demandant à l'utilisateur de choisir parmi les départements valides.
     *
     * @return Le département sélectionné par l'utilisateur.
     */
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
                throw new InputMismatchException("Département invalide. Choisissez parmi " + validDepartments
                        + ". Veuillez réessayer.");

            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    /**
     * Récupère un titre depuis la console en demandant à l'utilisateur de choisir parmi les titres valides.
     *
     * @return Le titre sélectionné par l'utilisateur.
     */
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
                throw new InputMismatchException("Titre invalide. Choisissez parmi " + validTitle
                        + ". Veuillez réessayer.");

            } catch (InputMismatchException e) {
                System.err.println(e.getMessage());
            }

        }
    }

    /**
     * Demande à l'utilisateur de saisir son âge et le valide.
     *
     * @return L'âge de l'utilisateur.
     */
    private double getAge() {
        double age;
        do {
            try {
                age = scanner.nextDouble();
                if (!(0 < age && age < 100))
                    throw new InputMismatchException();

            } catch (InputMismatchException e) {
                System.err.println("L'âge doit être compris entre 1 et 100. Veuillez réessayer.");
                age = 0.0;
                scanner.nextLine();
            }
        } while (age == 0.0);

        return age;
    }

    /**
     * Demande à l'utilisateur de saisir son année de naissance et la valide.
     *
     * @return L'année de naissance de l'utilisateur.
     */
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
                        (currentYear - 100) + " et " + (currentYear - 1) + ". Veuillez réessayer.");
                birthYear = 0;
                scanner.nextLine();
            }
        } while (birthYear == 0);

        return birthYear;
    }

    /**
     * Termine proprement le programme en fermant le scanner.
     */
    private void exit(PersonneDAO<?> personneDAO) {
        scanner.close();
        System.out.println("Au revoir !");
        System.exit(0);
    }
}
