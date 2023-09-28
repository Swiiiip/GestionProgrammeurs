package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;


public class GestionBddApp extends Application {
    static Stage primaryStage;
    static BorderPane rootLayout;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Gestion BDD");

        initRootLayout();
    }

    public static void addMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu"); // Rename the menu to "Menu" for clarity
        MenuItem returnMenuItem = new MenuItem("Revenir au menu");

        returnMenuItem.setOnAction(e -> {
            System.out.println("Menu clicked");
            rootLayout.getChildren().clear();
            Pages.showMenuPage(); // Show the menu page
        });

        menu.getItems().add(returnMenuItem); // Add the menu item to the menu
        menuBar.getMenus().add(menu); // Add the menu to the menu bar

        rootLayout.setTop(menuBar);
    }

    // Initialiser le layout
    private void initRootLayout() {
        try {
            primaryStage.setTitle("Gestion BDD");

            this.rootLayout = new BorderPane();
            Scene scene = new Scene(rootLayout, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();

            Pages.showMenuPage();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}