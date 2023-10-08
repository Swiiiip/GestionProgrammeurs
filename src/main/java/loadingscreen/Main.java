package loadingscreen;

import app.GestionBddApp;
import data.DataGenerator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage loadingStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ProgressBar progressBar = new ProgressBar();
        progressBar.setStyle("-fx-accent: #00C851;");

        Label loadingLabel = new Label("Chargement des données en cours...");
        loadingLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox loadingLayout = new VBox(10, loadingLabel, progressBar);
        loadingLayout.setAlignment(Pos.CENTER);
        StackPane loadingScreen = new StackPane(loadingLayout);

        Scene loadingScene = new Scene(loadingScreen, 400, 200);

        loadingStage = new Stage();
        loadingStage.setTitle("Chargement des données");
        loadingStage.setScene(loadingScene);
        loadingStage.setResizable(true);
        loadingStage.centerOnScreen();
        loadingStage.show();

        Thread dataGeneratorThread = new Thread(() -> {
            new DataGenerator(100, 50);

            Platform.runLater(() -> {
                loadingStage.hide();

                // Lancement de l'application principale
                GestionBddApp mainApp = new GestionBddApp();
                mainApp.start(new Stage());
            });
        });

        dataGeneratorThread.start();
    }
}
