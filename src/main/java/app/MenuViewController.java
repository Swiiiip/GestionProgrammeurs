package app;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;
import utils.Carousel;
import utils.MessageBar;

import java.sql.SQLException;
import java.util.List;

import static app.GestionBddApp.*;
import static javafx.application.Platform.exit;

public class MenuViewController {

    public static MenuBar initMenuBar() {

        MenuBar menuBar = new MenuBar();

        /* ----------- MENU BUTTONS ----------- */
        Menu menu = new Menu("Menus");
        MenuItem menuProgrammeur = new MenuItem("Menu Programmeur");

        menuProgrammeur.setOnAction(e -> {
            GestionBddApp.menuMode = Programmeur.class;
            getContentOverlay().getChildren().clear();

            VBox menuPage = Pages.getMenuPage();
            getContentOverlay().getChildren().add(menuPage);
        });


        MenuItem menuManager = new MenuItem("Menu Manager");

        menuManager.setOnAction(e -> {
            GestionBddApp.menuMode = Manager.class;
            getContentOverlay().getChildren().clear();

            VBox menuPage = Pages.getMenuPage();
            getContentOverlay().getChildren().add(menuPage);
        });


        menu.getItems().add(menuProgrammeur);
        menu.getItems().add(menuManager);
        menuBar.getMenus().add(menu);

        /* ----------- DATA BUTTONS ----------- */
        Menu data = new Menu("Data");

        Tooltip tooltip = new Tooltip("Synchrone la BDD avec le tableau affiché.");
        Label label = new Label("Fetch data");
        label.setStyle("-fx-text-fill: black;");
        label.setAlignment(Pos.CENTER_LEFT);
        label.setTooltip(tooltip);

        MenuItem fetchData = new MenuItem("", label);
        // (Pour quand la BDD est modifiée [via MySQL Workbench ou DataGenerator])
        fetchData.setOnAction(e -> {
            MessageBar messageBar = new MessageBar();
            try {
                VBox box = Pages.getDataDisplayPage(getProgrammeurDAO().getAll());
                getContentOverlay().getChildren().add(box);
                messageBar.displayMessageBar("Les données sont à jour !", MessageBar.MessageType.SUCCESS);

            } catch (SQLException err) {
                messageBar.displayMessageBar(err.getMessage(), MessageBar.MessageType.ERROR);
                throw new RuntimeException(err);
            }
        });


        tooltip = new Tooltip("Supprime l'intégralité des données de la BDD.");
        label = new Label("Delete all data");
        label.setAlignment(Pos.BOTTOM_RIGHT);
        label.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
        label.setTooltip(tooltip);

        MenuItem deleteAllData = new MenuItem("", label);

        deleteAllData.setOnAction(e -> {
            MessageBar messageBar = new MessageBar();
            try {
                getProgrammeurDAO().deleteAll();
                getManagerDAO().deleteAll();

                messageBar.displayMessageBar("Êtes-vous certain de tout vouloir supprimer ?", MessageBar.MessageType.WARNING);

            } catch (SQLException err) {
                messageBar.displayMessageBar(err.getMessage(), MessageBar.MessageType.ERROR);
                throw new RuntimeException(err);
            }
        });


        data.getItems().add(fetchData);
        data.getItems().add(deleteAllData);

        menuBar.getMenus().add(data);


        /* ----------- BACK BUTTON ----------- */
        Label backLabel = new Label("Back");

        tooltip = new Tooltip("Retourner à la page précédente.");
        backLabel.setTooltip(tooltip);

        backLabel.setOnMouseClicked(mouseEvent -> {

            ObservableList<Node> children = getContentOverlay().getChildren();
            int size = children.size();
            Pane container = getContainerMessageBar();

            if (size > 1 && !(size == 2 && children.contains(container)))
                if (children.get(size - 1).equals(container))
                    children.remove(size - 2);
                else
                    children.remove(size - 1);
        });

        menuBar.getMenus().add(new Menu("", backLabel));

        return menuBar;
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

        if (event.getSource() instanceof Button clickedButton) {
            String buttonText = clickedButton.getText();

            switch (buttonText) {
                case "Afficher tous les programmeurs":
                    try {
                        VBox box;
                        if(GestionBddApp.menuMode.equals(Programmeur.class))
                            box = Pages.getDataDisplayPage(getProgrammeurDAO().getAll());
                        else
                            box = Pages.getDataDisplayPage(getManagerDAO().getAll());

                        getContentOverlay().getChildren().add(box);

                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        new MessageBar().displayMessageBar(e.getMessage(), MessageBar.MessageType.ERROR);
                    }
                    break;

                case "Afficher un programmeur":
                    try {
                        HBox box = Pages.getProfileDataPage(getProgrammeurDAO().getById(1));
                        getContentOverlay().getChildren().add(box);
                        box.alignmentProperty().setValue(Pos.CENTER);

                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        new MessageBar().displayMessageBar(e.getMessage(), MessageBar.MessageType.ERROR);
                    }
                    break;

                case "Ajouter un programmeur":
                    try {
                        VBox box = Pages.getAddProfilePage();
                        getContentOverlay().getChildren().add(box);
                        box.alignmentProperty().setValue(Pos.CENTER);

                    } catch (Exception e) {
                        logger.error(e.getMessage());
                        new MessageBar().displayMessageBar(e.getMessage(), MessageBar.MessageType.ERROR);
                    }
                    break;

                    case "Générer plusieurs profiles":
                        try {
                            VBox box = Pages.getGenerateProfilesPage();
                            getContentOverlay().getChildren().add(box);
                            box.alignmentProperty().setValue(Pos.CENTER);

                        } catch (Exception e) {
                            logger.error(e.getMessage());
                            new MessageBar().displayMessageBar(e.getMessage(), MessageBar.MessageType.ERROR);
                        }
                        break;


                case "Quitter le programme":
                    exit();
                    break;

                default:
                    System.err.println("L'évènement n'est pas reconnu");
                    break;
            }
        }
    }
}
