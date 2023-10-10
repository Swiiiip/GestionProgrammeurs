package exec;

import data.DataGenerator;

public class Application {

    public static int NBPROGS;

    public static int NBMANAGERS;

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
