package loadingscreen;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * Cette classe représente un écran de chargement affiché à l'utilisateur pendant le chargement de données ou d'autres opérations.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 *
 */
public class LoadingScreen extends StackPane {

    /**
     * Constructeur de la classe LoadingScreen.
     * Crée un écran de chargement avec un libellé "Chargement en cours..." et un fond de couleur gris clair.
     */
    public LoadingScreen() {
        Label loadingLabel = new Label("Chargement en cours...");
        getChildren().add(loadingLabel);

        setStyle("-fx-background-color: lightgray;");
    }
}
