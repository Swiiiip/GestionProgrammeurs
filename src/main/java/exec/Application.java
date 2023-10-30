package exec;

import data.generator.DataGenerator;

/**
 * Cette classe représente l'application principale pour la gestion de personnes.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 *
 */
public class Application {

    /**
     * Le nombre de programmeurs.
     */
    public static int NBPROGS;

    /**
     * Le nombre de managers.
     */
    public static int NBMANAGERS;

    /**
     * Méthode principale de l'application.
     *
     * @param args les arguments passés en ligne de commande.
     */
    public static void main(String[] args) {
        AppliManagement application = new AppliManagement();
        application.start(args);

        long startTime = System.currentTimeMillis();

        new DataGenerator(NBPROGS, NBMANAGERS);

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        long secondes = executionTime / 1000;
        long minutes = secondes / 60;
        secondes = secondes % 60;

        System.out.println("Temps Réel : " + minutes + " minutes et " + secondes + " secondes.\n");

        new MenuPrincipal();
    }
}

