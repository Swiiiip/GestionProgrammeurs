package loadingscreen;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class LoadingScreen extends StackPane {

    public LoadingScreen() {
        Label loadingLabel = new Label("Chargement en cours...");
        getChildren().add(loadingLabel);

        // Personnalisez le style de l'écran de chargement
        setStyle("-fx-background-color: lightgray;");
    }
}
