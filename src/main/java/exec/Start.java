package exec;

/**
 * La classe de démarrage de l'application.
 * Cette classe contient la méthode principale (main) qui initialise et lance le menu de l'application.
 *
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class Start {

    /**
     * Méthode principale (main) de l'application.
     * Cette méthode initialise et démarre le menu de l'application.
     *
     * @param args Les arguments de la ligne de commande (non utilisés dans notre cas).
     */
    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }
}

