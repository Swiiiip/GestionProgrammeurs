package exec;

import data.ActionsBDDImpl;
import data.ProgrammeurBean;

import java.util.Scanner;

public class Menu extends ActionsBDDImpl {
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
                    // getAllProg();
                    break;
                case 2:
                    // getProgById();
                    id = getChoice();
                    System.out.print("Quel est l'id du programmeur ? : ");
                    prog = getProgById(id);
                    if(prog == null){
                        displayError("Le programmeur n'existe pas !");
                        choice = 0;
                    }else{
                        System.out.println(prog);
                    }

                    break;
                case 3:
                    // deleteProgById();
                    break;
                case 4:
                    // addProg();
                    break;
                case 5:
                    // setSalaryById();
                    break;
                case 6:
                    exit();
                    break;
                default:
                    displayError("/!\\ ERREUR /!\\ | Veuillez saisir un entier entre 1 et 6 !");
                    break;
            }
        }while(choice<1 || choice>6 );

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

    public void displayError(String message){
        System.err.println("\n" + message+"\n");
    }



}
