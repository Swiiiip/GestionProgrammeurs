package exec;

import java.util.Scanner;

public class AppliManagement extends Application{

    public AppliManagement(){
    }

    /**
     * Allows to launch the program and to manage potential errors
     *
     * @param args the arguments provided by the user
     */
    public void start(String[] args){
        try {
            validationArgs(args);
            displaySuccess("\nArguments :" + "\n  >nombre de programmeurs : " + NBPROGS +
                    "\n  >nombre de managers : " + NBMANAGERS);
        }
        catch(IllegalArgumentException e) {
            displayError(e);
            loadMenu();
        }
    }

    /**
     * Validates the arguments
     * If no argument is given the menu is loaded
     *
     * @param args the arguments
     */
    private static void validationArgs(String[] args) throws IllegalArgumentException {
        if (args.length < 2) {
            String msg = "Missing arguments : ";
            switch (args.length) {
                case 0:
                    msg += "nb progs ";
                case 1:
                    msg += "nb managers ";
                    break;
                default:
                    break;
            }
            throw new IllegalArgumentException(msg);
        }
        else {
            for (int i = 0; i < args.length; i++) {
                switch (i) {
                    case 0:
                        int nbProgs;
                        try {
                            nbProgs = Integer.parseInt(args[i]);

                            if (nbProgs <= 0)
                                throw new IllegalArgumentException("I need a positiv int");

                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid format : I need a int");
                        }
                        NBPROGS = nbProgs;
                        break;

                    case 1:
                        int nbManagers;
                        try {
                            nbManagers = Integer.parseInt(args[i]);

                            if (nbManagers <= 0)
                                throw new IllegalArgumentException("I need a positiv int");

                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("Invalid format : I need a int");
                        }
                        NBMANAGERS = nbManagers;
                        break;

                    default:
                        break;
                }
            }
        }
    }

    /**
     * loads the initialization menu
     */
    private void loadMenu() {
        displaySuccess("MAIN MENU");
        Scanner scanner = new Scanner(System.in);
        System.out.println("[0] - Exit\n");

        if (NBPROGS == 0) {
            int nbProgs;

            do {
                System.out.print("Choose number of programmeurs: ");
                try{
                    nbProgs = scanner.nextInt();
                    exit(String.valueOf(nbProgs));
                } catch (NumberFormatException e){
                    nbProgs = 0;
                }
            } while(validData(nbProgs));
            NBPROGS = nbProgs;
        } else
            System.out.println("\nThe number of programmeurs in the database is : " + NBPROGS + "\n");

        if (NBMANAGERS == 0) {
            int nbManager;
            do {
                System.out.print("Choose number of managers: ");
                try{
                    nbManager = scanner.nextInt();
                    exit(String.valueOf(nbManager));
                } catch (NumberFormatException e){
                    nbManager = 0;
            }
            } while (validData(nbManager));
            NBMANAGERS = nbManager;
            System.out.println();
        }else
            System.out.println("\nThe number of managers in the database is : " + NBMANAGERS + "\n");

    }

    private boolean validData(int data) {
        if(data <= 0 ){
            displayError("The minimum number is 1.");
            return true;
        }  else return false;
    }

    /**
     * Displays a success message
     * @param msg the message to display
     */
    private void displaySuccess(String msg){
        System.out.println(msg + "\n");
    }

    /**
     * Allows you to display an error message
     * @param msg the message to display
     */
    public static void displayError(String msg){
        System.out.println("Error: " + msg + "\n");
    }

    /**
     * Allows you to display the error message of an exception
     * @param e Exception to handle
     */
    public void displayError(Exception e){
        displayError(e.getMessage());
    }

    /**
     * allows you to exit the program
     * @param exit the string typed
     */
    private void exit(String exit) {
        if (exit.equals("0")) {
            System.out.println("You exited the program");
            System.exit(0);
        }
    }


}
