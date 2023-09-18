package exec;

import data.ActionsBDDImpl;
import data.ProgrammeurBean;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Menu extends ActionsBDDImpl {

    private static final Scanner sc = new Scanner(System.in);

    public void start() {
        int choice;
        int id;
        ProgrammeurBean prog = null;

        do{
            displayMenu();
            try{
                choice = getChoice();
            } catch (Exception e) {
                choice = 0;
            }
            switch (choice) {
                case 1:
                    try {
                        List<ProgrammeurBean> progs = getAllProg();
                        displayAllProgs(progs);
                    } catch (SQLException e) {
                        displayError("Il n'y a aucun programmeurs dans notre base de données...");
                    }
                    break;

                case 2:
                    System.out.print("Id du programmeur à afficher : ");

                    do{
                        id = getChoice();
                        try {
                            prog = getProgById(id);
                        } catch (SQLException e) {
                            id = 0;
                            displayError("Recherche KO. Saisissez à nouveau l'id : ");
                        }
                    }while(id == 0);

                    displayProg(prog);
                    break;

                case 3:
                    System.out.print("Id du programmeur à supprimer : ");

                    do{
                        id = getChoice();
                        try {
                            deleteProgById(id);
                        } catch (SQLException e) {
                            id = 0;
                            displayError("Suppression KO. Saisissez à nouveau l'id : ");
                        }
                    }while(id == 0);

                    System.out.println("SUPPRESSION REUSSIE !\n");
                    break;

                case 4:
                    try {
                        prog = getProg();
                        addProg(prog);
                    } catch (SQLException e) {
                        displayError("Ajout KO. Connexion à la base de données interrompue!");
                    }

                    System.out.println("AJOUT REUSSI !\n");
                    break;

                case 5:
                    System.out.print("Id du programmeur : ");

                    do{
                        id = getChoice();
                        try {
                            getProgById(id);
                        } catch (SQLException e) {
                            id = 0;
                            displayError("Programmeur introuvable. Saisissez à nouveau l'id : ");
                        }
                    }while(id == 0);

                    float salary = getSalary();

                    try{
                        setSalaryById(id, salary);
                    } catch(SQLException e) {
                        displayError("Modification KO. Connexion à la base de données interrompue!");
                    }

                    System.out.println("MODIFICATION REUSSIE !\n");
                    break;

                case 6:
                    System.out.print("\nAu revoir !");
                    exit();
                    break;

                default:
                    displayError("/!\\ ERREUR /!\\ | Veuillez saisir un entier entre 1 et 6 !");
                    break;
            }
        }while(choice<1 || choice >6);

        sc.close();
    }

    public void displayMenu() {
        System.out.println("\n<<<<<<<<<<<<  MENU  >>>>>>>>>>>>\n");
        System.out.println("1. Afficher tous les programmeurs");
        System.out.println("2. Afficher un programmeur");
        System.out.println("3. Supprimer un programmeur");
        System.out.println("4. Ajouter un programmeur");
        System.out.println("5. Modifier le salaire");
        System.out.println("6. Quitter le programme\n");
        System.out.print("Quel est votre choix ? : ");
    }

    public int getChoice() {
        Scanner sc = new Scanner(System.in);

        return sc.nextInt();
    }

    public float getSalary(){
        return sc.nextFloat();
    }

    public void displayError(String message){
        System.err.println("\n" + message+"\n");
    }

    public void displayAllProgs(List<ProgrammeurBean> progs){
        for(ProgrammeurBean prog : progs){
            displayProg(prog);
        }
    }

    public void displayProg(ProgrammeurBean prog){
        System.out.println(prog);
        System.out.println("---------------------------------------------------------------------------------------\n");
    }

    public ProgrammeurBean getProg(){
        System.out.print("Nom du programmeur : ");
        String firstName = sc.next();

        System.out.print("Prénom du programmeur : ");
        String lastName = sc.next();

        System.out.print("Adresse du programmeur : ");
        String address = sc.next();

        System.out.print("Pseudo du programmeur : ");
        String pseudo = sc.next();

        System.out.print("Responsable du programmeur : ");
        String manager = sc.next();

        System.out.print("Hobby du programmeur : ");
        String hobby = sc.next();

        System.out.print("Année de naissance du programmeur : ");
        int birthYear = sc.nextInt();

        System.out.print("Salaire du programmeur : ");
        float salary = sc.nextFloat();

        System.out.print("Prime du programmeur : ");
        float prime = sc.nextFloat();

        return new ProgrammeurBean(firstName, lastName, address, pseudo, manager, hobby, birthYear, salary, prime);


    }

}
