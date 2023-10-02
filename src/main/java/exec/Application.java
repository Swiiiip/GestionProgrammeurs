package exec;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;
import data.DataGenerator;

public class Application {

    public static int NBPROGS;

    public static int NBMANAGERS;

    public static void main(String[] args) {
        AppliManagement application = new AppliManagement();
        application.start(args);

        long estimationTime = (NBPROGS + NBMANAGERS) / 5 * 1000L;

        estimationTime = Math.round(estimationTime * 0.95);

        long secondes = estimationTime / 1000;
        long minutes = secondes / 60;
        secondes = secondes % 60;

        System.out.println("Temps Estimé : " + minutes + " minutes et " + secondes + " secondes.");

        long startTime = System.currentTimeMillis();

        new DataGenerator(NBPROGS, NBMANAGERS);

        long endTime = System.currentTimeMillis();

        long executionTime = endTime - startTime;

        secondes = executionTime / 1000;
        minutes = secondes / 60;
        secondes = secondes % 60;

        System.out.println("Temps Réel : " + minutes + " minutes et " + secondes + " secondes.");


        ProgrammeurDAO prog = new ProgrammeurDAO();

        ManagerDAO manager = new ManagerDAO();

        //TODO
    }
}
