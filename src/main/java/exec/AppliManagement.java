package exec;

import java.util.Scanner;

public class AppliManagement extends Application {

    public AppliManagement() {
    }

    /**
     * Validates the arguments
     * If no argument is given the menu is loaded
     *
     * @param args the arguments
     */
    private static void validationArgs(String[] args) throws IllegalArgumentException {
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
    }

    /**
     * Allows you to display an error message
     *
     * @param msg the message to display
     */
    public static void displayError(String msg) {
        System.err.println(msg);
    }

    /**
     * Allows to launch the program and to manage potential errors
     *
     * @param args the arguments provided by the user
     */
    public void start(String[] args) {
        try {
            validationArgs(args);
            displaySuccess("\nArguments :" + "\n\t>nombre de programmeurs : " + NBPROGS +
                    "\n\t>nombre de managers : " + NBMANAGERS);
        } catch (IllegalArgumentException e) {
            displayError(e);
            loadMenu();
        }
    }

    /**
     * loads the initialization menu
     */
    private void loadMenu() {
        Scanner scanner = new Scanner(System.in);
        displaySuccess("MAIN MENU");
        System.out.println("[0] - Exit\n");

        if (NBPROGS == 0) {
            int nbProgs;

            do {
                System.out.print("Choose number of programmeurs: ");
                try {
                    nbProgs = Integer.parseInt(scanner.next());
                    exit(String.valueOf(nbProgs));

                    if (nbProgs <= 0)
                        displayError("The minimum number is 1.");
                } catch (Exception e) {
                    nbProgs = 0;
                    displayError("The minimum number is 1.");
                }
            } while (nbProgs <= 0);
            NBPROGS = nbProgs;
        } else
            System.out.println("\nFor this instance, you set up : " + NBPROGS + " programmeurs\n");

        if (NBMANAGERS == 0) {
            int nbManager;
            do {
                System.out.print("Choose number of managers: ");
                try {
                    nbManager = Integer.parseInt(scanner.next());
                    exit(String.valueOf(nbManager));

                    if (nbManager <= 0)
                        displayError("The minimum number is 1.");
                } catch (Exception e) {
                    nbManager = 0;
                    displayError("The minimum number is 1.");
                }
            } while (nbManager <= 0);
            NBMANAGERS = nbManager;
            System.out.println();
        } else
            System.out.println("\nFor this instance, you set up : " + NBMANAGERS + " managers\n");

    }

    /**
     * Displays a success message
     *
     * @param msg the message to display
     */
    private void displaySuccess(String msg) {
        System.out.println(msg + "\n");
    }

    /**
     * Allows you to display the error message of an exception
     *
     * @param e Exception to handle
     */
    public void displayError(Exception e) {
        displayError(e.getMessage());
    }

    /**
     * allows you to exit the program
     *
     * @param exit the string typed
     */
    private void exit(String exit) {
        if (exit.equals("0")) {
            System.out.println("You exited the program");
            System.exit(0);
        }
    }


}
