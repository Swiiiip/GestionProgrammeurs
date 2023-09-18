package exec;

import data.ProgrammeurBean;

public class Start {
    public static void main(String[] args) {
        Menu menu = new Menu();
        ProgrammeurBean prog = new ProgrammeurBean(1, "Doe", "John", "1 rue de la paix", "jdoe", "jdoe", "foot", 1980, 2000, 100);
        System.out.println(prog);
        menu.start();
    }
}
