package exec;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;
import data.DataGenerator;
import prediction.PredictionModel;


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

        System.out.println("Temps Réel : " + minutes + " minutes et " + secondes + " secondes.");

        ProgrammeurDAO prog = new ProgrammeurDAO();
        ManagerDAO manager = new ManagerDAO();

        try {
            PredictionModel<ProgrammeurDAO> progModel = new PredictionModel<>(prog);
            PredictionModel<ManagerDAO> managerModel = new PredictionModel<>(manager);
            System.out.println(progModel.predictSalary(50, "male"));
            System.out.println(managerModel.predictSalary(50, "female"));
        } catch(Exception e){
            System.err.println(e.getMessage());
        }
    }
}
