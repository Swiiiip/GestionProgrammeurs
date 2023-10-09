package app;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GestionBddApp extends Application {
    protected static Stage primaryStage;
    protected static BorderPane rootLayout;
    protected static ProgrammeurDAO programmeurDAO = new ProgrammeurDAO();
    protected static ManagerDAO managerDAO = new ManagerDAO();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        GestionBddApp.primaryStage = primaryStage;
        primaryStage.setTitle("Gestion BDD");

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            rootLayout = new BorderPane();
            rootLayout.setTop(MenuViewController.initMenuBar());

            Scene scene = new Scene(rootLayout, 400, 400);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            primaryStage.setScene(scene);

            Pages.showMenuPage();

            primaryStage.show();

        } catch (Exception e) {
            System.err.println("Error : " + e.getMessage());
        }
    }

}