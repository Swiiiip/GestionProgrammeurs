package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class Pages extends GestionBddApp {
    private static final BorderPane rootLayout = GestionBddApp.rootLayout;

    public static void showMenuPage() {
        try {
            primaryStage.setTitle("Gestion BDD | Menu");

            FXMLLoader loader = new FXMLLoader(MenuViewController.class.getResource("Menu.fxml"));
            VBox menuLayout = loader.load();
            addMenuBar();
            rootLayout.setCenter(menuLayout);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void showDataDisplayPage() {
        try {
            primaryStage.setTitle("Gestion BDD | Data Display");

            FXMLLoader loader = new FXMLLoader(MenuViewController.class.getResource("DataDisplay.fxml"));
            VBox menuLayout = loader.load();
            addMenuBar();
            rootLayout.setCenter(menuLayout);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

