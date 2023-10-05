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
    protected static MenuViewController menuViewController = new MenuViewController();
    protected static DataViewController dataViewController = new DataViewController();
    protected static ProfileViewController profileViewController = new ProfileViewController();
    protected static Pages pages = new Pages();
    protected static ProgrammeurDAO programmeurDAO = new ProgrammeurDAO();
    protected static ManagerDAO managerDAO = new ManagerDAO();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Gestion BDD");

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            this.rootLayout = new BorderPane();
            Scene scene = new Scene(rootLayout, 400, 400);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);

            rootLayout.setTop(menuViewController.initMenuBar());

            Pages.showMenuPage();

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}