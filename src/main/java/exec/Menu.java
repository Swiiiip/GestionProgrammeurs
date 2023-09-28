package exec;

import data.ActionsBD;
import data.DataGenerator;
import javafx.scene.chart.PieChart;
import personnes.ManagerBean;
import personnes.ProgrammeurBean;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;


/**
 * La classe Menu gère un menu interactif permettant à l'utilisateur d'effectuer
 * différentes opérations sur une base de données de programmeurs.
 *
 * L'utilisateur peut afficher tous les programmeurs, afficher un programmeur en
 * particulier, supprimer un programmeur, ajouter un programmeur, modifier le salaire
 * d'un programmeur ou quitter le programme.
 *
 * Cette classe étend ActionsBDDImpl, ce qui suggère qu'elle implémente des actions
 * liées à la base de données.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class Menu {

    ActionsBD actions = new ActionsBD();

    /**
     * Scanner utilisé pour lire l'entrée utilisateur.
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     * Choix de l'utilisateur.
     */
    private int choice;

    /**
     * Identifiant d'un programmeur.
     */
    private int id;

    /**
     * Objet ProgrammeurBean pour stocker les données d'un programmeur.
     */
    private ProgrammeurBean prog;

    /**
     * Constructeur par défaut de la classe Menu. Initialise les variables de choix,
     * d'identifiant et de programmeur.
     */
    public Menu(){
        this.choice = 0;
        this.id = 0;
        this.prog = new ProgrammeurBean();
        DataGenerator dataGenerator = new DataGenerator();
    }

    /**
     * Démarre l'application et gère le menu interactif. L'utilisateur peut choisir
     * parmi différentes options pour interagir avec la base de données des programmeurs.
     */
    public void start() {

        boolean on = true;
        do{
            displayMenu();
            try{
                this.choice = getChoice();
            } catch (Exception e) {
                this.choice = 0;
            }
            switch (this.choice) {
                case 1:
                    try {
                        List<ProgrammeurBean> progs = this.actions.getAllProg();
                        displayAllProgs(progs);
                        System.out.println(progs.size() + " programmeurs trouvés.\n");
                    } catch (Exception e) {
                        displayError(e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Id du programmeur à afficher : ");

                    do{
                        this.id = getChoice();
                        try {
                            this.prog = this.actions.getProgById(id);
                        } catch (SQLException e) {
                            this.id = 0;
                            displayError("Recherche KO. Saisissez à nouveau l'id : ");
                        }
                    }while(this.id == 0);

                    displayProg(this.prog);
                    break;

                case 3:
                    System.out.print("Id du programmeur à supprimer : ");

                    do{
                        this.id = getChoice();
                        try {
                            this.actions.deleteProgById(this.id);
                        } catch (SQLException e) {
                            this.id = 0;
                            displayError("Suppression KO. Saisissez à nouveau l'id : ");
                        }
                    }while(this.id == 0);

                    System.out.println("SUPPRESSION REUSSIE !\n");
                    break;

                case 4:
                    try {
                        this.prog = getProg();
                        this.actions.addProg(this.prog);
                    } catch (SQLException e) {
                        displayError("Ajout KO. Connexion à la base de données interrompue!");
                    }

                    System.out.println("AJOUT REUSSI !\n");
                    break;

                case 5:
                    System.out.print("Id du programmeur : ");

                    do{
                        this.id = getChoice();
                        try {
                            this.actions.getProgById(this.id);
                        } catch (SQLException e) {
                            this.id = 0;
                            displayError("Programmeur introuvable. Saisissez à nouveau l'id : ");
                        }
                    }while(this.id == 0);

                    float salary = getSalary();

                    try{
                        this.actions.setProgSalaryById(this.id, salary);
                    } catch(SQLException e) {
                        displayError("Modification KO. Connexion à la base de données interrompue!");
                    }

                    System.out.println("MODIFICATION REUSSIE !\n");
                    break;

                case 6:
                    System.out.print("\nAu revoir !");
                    on = false;
                    this.actions.exit();
                    break;

                default:
                    displayError("/!\\ ERREUR /!\\ | Veuillez saisir un entier entre 1 et 6 !");
                    break;
            }
        }while(on);

        sc.close();
    }

    /**
     * Affiche un menu interactif à l'utilisateur, présentant les différentes options
     * disponibles pour interagir avec la base de données des programmeurs. Les options
     * incluent l'affichage de tous les programmeurs, l'affichage d'un programmeur
     * spécifique, la suppression d'un programmeur, l'ajout d'un nouveau programmeur,
     * la modification du salaire d'un programmeur, et la sortie du programme.
     *
     * Le menu affiché contient une liste numérotée d'options permettant à l'utilisateur
     * de sélectionner l'action souhaitée en saisissant le numéro correspondant.
     */
    public void displayMenu() {
        System.out.println("\n\n<<<<<<<<<<<<  MENU  >>>>>>>>>>>>\n");
        System.out.println("1. Afficher tous les programmeurs");
        System.out.println("2. Afficher un programmeur");
        System.out.println("3. Supprimer un programmeur");
        System.out.println("4. Ajouter un programmeur");
        System.out.println("5. Modifier le salaire");
        System.out.println("6. Quitter le programme\n");
        System.out.print("Quel est votre choix ? : ");
    }

    /**
     * Obtient un entier correspondant au choix d'action à partir de l'entrée utilisateur.
     *
     * @return L'entier saisi par l'utilisateur.
     */
    public int getChoice() {
        return sc.nextInt();
    }

    /**
     * Obtient un montant de salaire à partir de l'entrée utilisateur.
     *
     * @return Le montant du salaire saisi par l'utilisateur.
     */
    public float getSalary() {
        System.out.print("Nouveau salaire de ce programmeur: ");
        return sc.nextFloat();
    }

    /**
     * Affiche un message d'erreur à l'utilisateur.
     *
     * @param message Le message d'erreur à afficher.
     */
    public void displayError(String message){
        System.err.print("\n" + message);
    }

    /**
     * Affiche les informations de tous les programmeurs contenus dans une liste.
     * Pour afficher les détails de chaque programmeur individuellement, cette méthode
     * utilise la méthode {@link #displayProg(ProgrammeurBean)}.
     *
     * @param progs Liste des programmeurs à afficher.
     */
    public void displayAllProgs(List<ProgrammeurBean> progs){
        for(ProgrammeurBean prog : progs){
            displayProg(prog);
        }
    }

    /**
     * Affiche les informations d'un programmeur, y compris son identifiant en base de données
     * son nom, prénom, adresse, pseudo, responsable, hobby, année de naissance, salaire et prime.
     *
     * @param prog ProgrammeurBean contenant les informations à afficher.
     */
    public void displayProg(ProgrammeurBean prog){
        System.out.print(prog);
        System.out.println("---------------------------------------------------------------------------------------\n");
    }

    /**
     * Obtient les informations d'un nouveau programmeur à partir de l'entrée
     * utilisateur en demandant diverses données telles que le nom, le prénom,
     * l'adresse, le pseudo, le responsable, le passe-temps, l'année de naissance,
     * le salaire et la prime. Ces informations sont utilisées pour créer un
     * nouvel objet ProgrammeurBean représentant le programmeur.
     *
     * @return Un nouvel objet ProgrammeurBean initialisé avec les informations
     *         saisies par l'utilisateur pour le nouveau programmeur.
     */
    public ProgrammeurBean getProg(){
        ManagerBean manager = null;
        System.out.print("Nom du programmeur : ");
        String firstName = sc.next();

        System.out.print("Prénom du programmeur : ");
        String lastName = sc.next();

        System.out.print("Adresse du programmeur : ");
        String address = sc.nextLine();

        System.out.print("Pseudo du programmeur : ");
        String pseudo = sc.next();


        do {
            System.out.print("Responsable du programmeur : ");
            String lastNameManager = sc.next();
            String firstNameManager = sc.next();
            try {
                manager = this.actions.getManagerByFullName(lastNameManager, firstNameManager);

                if (manager == null)
                    throw new SQLException();
            } catch (SQLException e) {
                displayError("Aucun responsable avec ce nom et prénom existe!");
            }
        }while(manager==null);

        System.out.print("Hobby du programmeur : ");
        String hobby = sc.nextLine();

        System.out.print("Année de naissance du programmeur : ");
        int birthYear = sc.nextInt();

        System.out.print("Salaire du programmeur : ");
        float salary = sc.nextFloat();

        System.out.print("Prime du programmeur : ");
        float prime = sc.nextFloat();

        return new ProgrammeurBean(firstName, lastName, address, pseudo, manager, hobby, birthYear, salary, prime);
    }

}
