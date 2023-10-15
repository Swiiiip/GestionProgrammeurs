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
import utils.MessageBar;

import java.util.Objects;

public class GestionBddApp extends Application {

    public static final Logger logger = LoggerFactory.getLogger(GestionBddApp.class);
    private static final StackPane contentOverlay = new StackPane();
    private static final Pane containerPane = new Pane();
    protected static Stage primaryStage;
    protected static BorderPane rootLayout;
    protected static ProgrammeurDAO programmeurDAO = new ProgrammeurDAO();
    protected static ManagerDAO managerDAO = new ManagerDAO();

    public static void main(String[] args) {
        launch(args);
    }

    public static StackPane getContentOverlay() {
        return contentOverlay;
    }

    public static Pane getContainerMessageBar() {
        return containerPane;
    }

    @Override
    public void start(Stage primaryStage) {
        GestionBddApp.primaryStage = primaryStage;
        primaryStage.setTitle("Gestion BDD");

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

            Scene scene = new Scene(rootLayout, 400, 400);
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