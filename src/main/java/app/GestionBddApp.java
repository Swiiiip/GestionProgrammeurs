package app;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class GestionBddApp extends Application {
    protected static Stage primaryStage;
    protected static final Logger logger = LoggerFactory.getLogger(GestionBddApp.class);
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
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

            primaryStage.setScene(scene);

            Pages.showMenuPage();

            primaryStage.show();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}