import data.ManagerBean;
import data.ProgrammeurBean;
public class Test {
    public static void main(String[] args) {
        ManagerBean manager = new ManagerBean("Hatoum", "Jade", "10 Rue de la Paix", "Jeux-vidéos", 2003, 13329.3f, 332.3f, "informatique");
        ProgrammeurBean prog = new ProgrammeurBean("Alonso", "Cédric", "115 Rue de la République", "MrRed", manager, "Jeux-vidéos", 2002, 23232.32f, 321f);

        System.out.println(prog);

    }
}
