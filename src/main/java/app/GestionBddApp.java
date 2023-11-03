package app;

import dao.ManagerDAO;
import dao.ProgrammeurDAO;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personnes.Programmeur;
import utils.MessageBar;

import java.util.Objects;

public class GestionBddApp extends Application {

    public static final Logger logger = LoggerFactory.getLogger(GestionBddApp.class);
    public static Class menuMode = Programmeur.class;
    private static final StackPane contentOverlay = new StackPane();
    private static final Pane containerPane = new Pane();
    protected static Stage primaryStage;
    protected static BorderPane rootLayout;
    private static ProgrammeurDAO programmeurDAO = new ProgrammeurDAO();
    private static ManagerDAO managerDAO = new ManagerDAO();

    public static void main(String[] args) {
        launch(args);
    }

    public static StackPane getContentOverlay() {
        return contentOverlay;
    }

    public static Pane getContainerMessageBar() {
        return containerPane;
    }

    public static ProgrammeurDAO getProgrammeurDAO() {
        return programmeurDAO;
    }

    public static ManagerDAO getManagerDAO() {
        return managerDAO;
    }

    @Override
    public void start(Stage primaryStage) {
        GestionBddApp.primaryStage = primaryStage;
        primaryStage.setTitle("Gestion BDD | "+menuMode.getSimpleName());

        initRootLayout();
    }

    private void initRootLayout() {
        try {
            rootLayout = new BorderPane();
            rootLayout.setTop(MenuViewController.initMenuBar());

            rootLayout.setCenter(contentOverlay);
            contentOverlay.setAlignment(Pos.CENTER);

            contentOverlay.getChildren().add(containerPane);
            containerPane.setMouseTransparent(true);

            Scene scene = new Scene(rootLayout, 600, 430);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

            primaryStage.setScene(scene);

            VBox menuPage = Pages.getMenuPage();
            getContentOverlay().getChildren().add(menuPage);

            primaryStage.show();

        } catch (Exception e) {
            logger.error(e.getMessage());
            new MessageBar().displayMessageBar(e.getMessage(), MessageBar.MessageType.ERROR);
        }
    }

}