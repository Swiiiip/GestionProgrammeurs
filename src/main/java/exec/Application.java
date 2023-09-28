package exec;

import data.DataGenerator;

public class Application {

    public static int NBPROGS = 0;

    public static int NBMANAGERS = 0;

    public static void main(String[] args) {
        AppliManagement application = new AppliManagement();
        application.start(args);

        new DataGenerator(NBPROGS, NBMANAGERS);

        Menu menu = new Menu();
        menu.start();
    }
}
