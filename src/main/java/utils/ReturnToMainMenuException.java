package utils;

/**
 * Cette classe représente une exception personnalisée qui est levée pour indiquer un retour au menu principal de l'application.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 *
 */
public class ReturnToMainMenuException extends RuntimeException {
    /**
     * Constructeur par défaut de l'exception.
     */
    public ReturnToMainMenuException() {
        super("Retour au menu principal");
    }
}

