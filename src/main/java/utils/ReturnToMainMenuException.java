package utils;

public class ReturnToMainMenuException extends RuntimeException {
    public ReturnToMainMenuException() {
        super("Retour au menu principal");
    }
}
