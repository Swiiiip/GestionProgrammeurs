package exec;

import java.util.Scanner;

/**
 * Cette classe représente une application de gestion et d'initialisation.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 *
 */
public class AppliManagement extends Application {

    /**
     * Constructeur par défaut de l'application de gestion.
     */
    public AppliManagement() {
    }

    /**
     * Valide les arguments passés à l'application.
     * Si aucun argument n'est fourni, le menu est chargé.
     *
     * @param args les arguments passés à l'application.
     * @throws IllegalArgumentException si les arguments sont invalides.
     */
    private static void validationArgs(String[] args) throws IllegalArgumentException {
        for (int i = 0; i < args.length; i++) {
            switch (i) {
                case 0:
                    int nbProgs;
                    try {
                        nbProgs = Integer.parseInt(args[i]);

                        if (nbProgs <= 0)
                            throw new IllegalArgumentException("J'ai besoin d'un entier positif");

                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Format invalide : j'ai besoin d'un entier");
                    }
                    NBPROGS = nbProgs;
                    break;

                case 1:
                    int nbManagers;
                    try {
                        nbManagers = Integer.parseInt(args[i]);

                        if (nbManagers <= 0)
                            throw new IllegalArgumentException("J'ai besoin d'un entier positif");

                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("Format invalide : j'ai besoin d'un entier");
                    }
                    NBMANAGERS = nbManagers;
                    break;

                default:
                    break;
            }
        }
        if (args.length < 2) {
            String msg = "Arguments manquants : ";
            switch (args.length) {
                case 0:
                    msg += "nb de programmeurs ";
                case 1:
                    msg += "nb de managers ";
                    break;
                default:
                    break;
            }
            throw new IllegalArgumentException(msg);
        }
    }

    /**
     * Permet d'afficher un message d'erreur.
     *
     * @param msg le message à afficher.
     */
    public static void displayError(String msg) {
        System.err.println(msg);
    }

    /**
     * Permet de lancer le programme et de gérer les erreurs éventuelles.
     *
     * @param args les arguments fournis par l'utilisateur.
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
     * Charge le menu d'initialisation.
     */
    private void loadMenu() {
        Scanner scanner = new Scanner(System.in);
        displaySuccess("MENU PRINCIPAL");
        System.out.println("[0] - Quitter\n");

        if (NBPROGS == 0) {
            int nbProgs;

            do {
                System.out.print("Choisissez le nombre de programmeurs : ");
                try {
                    nbProgs = Integer.parseInt(scanner.next());
                    exit(String.valueOf(nbProgs));

                    if (nbProgs <= 0)
                        displayError("Le nombre minimum est 1.");
                } catch (Exception e) {
                    nbProgs = 0;
                    displayError("Le nombre minimum est 1.");
                }
            } while (nbProgs <= 0);
            NBPROGS = nbProgs;
        } else
            System.out.println("\nPour cette instance, vous avez configuré : " + NBPROGS + " programmeurs\n");

        if (NBMANAGERS == 0) {
            int nbManagers;
            do {
                System.out.print("Choisissez le nombre de managers : ");
                try {
                    nbManagers = Integer.parseInt(scanner.next());
                    exit(String.valueOf(nbManagers));

                    if (nbManagers <= 0)
                        displayError("Le nombre minimum est 1.");
                } catch (Exception e) {
                    nbManagers = 0;
                    displayError("Le nombre minimum est 1.");
                }
            } while (nbManagers <= 0);
            NBMANAGERS = nbManagers;
            System.out.println();
        } else
            System.out.println("\nPour cette instance, vous avez configuré : " + NBMANAGERS + " managers\n");
    }

    /**
     * Affiche un message de réussite.
     *
     * @param msg le message à afficher.
     */
    private void displaySuccess(String msg) {
        System.out.println(msg + "\n");
    }

    /**
     * Permet d'afficher le message d'erreur d'une exception.
     *
     * @param e l'exception à gérer.
     */
    public void displayError(Exception e) {
        displayError(e.getMessage());
    }

    /**
     * Permet de quitter le programme.
     *
     * @param exit la chaîne de caractères entrée.
     */
    private void exit(String exit) {
        if (exit.equals("0")) {
            System.out.println("Vous avez quitté le programme");
            System.exit(0);
        }
    }
}

