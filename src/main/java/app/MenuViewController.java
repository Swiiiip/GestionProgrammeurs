package app;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import utils.MessageBar;

import java.sql.SQLException;

import static app.GestionBddApp.*;
import static javafx.application.Platform.exit;

public class MenuViewController {

    @FXML
    private void handleButtonAction(ActionEvent event) {
        VBox box;

        if (event.getSource() instanceof Button clickedButton) {
            String buttonText = clickedButton.getText();

            switch (buttonText) {
                case "Afficher tous les programmeurs":
                    try {
                        //Pages.showDataDisplayPage(programmeurDAO.getAll());
                        box = Pages.getDataDisplayPage(programmeurDAO.getAll());
                        getContentOverlay().getChildren().add(box);

                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        new MessageBar().displayMessageBar(e.getMessage(), MessageBar.MessageType.ERROR);
                    }
                    break;

                case "Afficher un programmeur":
                    try {
                        //TODO input id with integer check
                        box = Pages.getProfileData(programmeurDAO.getById(1));
                        getContentOverlay().getChildren().add(box);

                    } catch (SQLException e) {
                        logger.error(e.getMessage());
                        new MessageBar().displayMessageBar(e.getMessage(), MessageBar.MessageType.ERROR);
                    }
                    break;

                case "Supprimer un programmeur":
                    logger.info("suppr");
                    break;

                case "Ajouter un programmeur":
                    logger.info("add");
                    break;

                case "Modifier le salaire":
                    logger.info("update");
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

    public static MenuBar initMenuBar() {

        MenuBar menuBar = new MenuBar();

        /* ----------- MENU BUTTONS ----------- */
        Menu menu = new Menu("Menu");
        MenuItem returnMenuItem = new MenuItem("Menu principal");

        returnMenuItem.setOnAction(e -> {
            getContentOverlay().getChildren().clear(); //TODO OPTIONAL FUNCTIONALITY : clears page stack (=history) when returning back to main menu

            VBox menuPage = Pages.getMenuPage();
            getContentOverlay().getChildren().add(menuPage);
        });

        menu.getItems().add(returnMenuItem);
        menuBar.getMenus().add(menu);

        /* ----------- FETCH DATA BUTTON ----------- */
        // (Pour quand la BDD est modifiée [via MySQL Workbench ou DataGenerator])
        Label label = new Label("Fetch Data");

        Tooltip tooltip = new Tooltip("Remet à jour les données affichées en synchronisant avec la BDD.");
        label.setTooltip(tooltip);

        label.setOnMouseClicked(mouseEvent -> {
            try {
                VBox box = Pages.getDataDisplayPage(programmeurDAO.getAll());
                getContentOverlay().getChildren().add(box);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        menuBar.getMenus().add(new Menu("", label));

        /* ----------- TMP BUTTON ----------- */  //TODO temp button to test messageBar display, REMOVE LATER!!!
        label = new Label("messageBar");

        label.setOnMouseClicked(mouseEvent -> new MessageBar().displayMessageBar("test", MessageBar.MessageType.SUCCESS));

        menuBar.getMenus().add(new Menu("", label));

        /* ----------- BACK BUTTON ----------- */
        label = new Label("Back");

        tooltip = new Tooltip("Retourner à la page précédente.");
        label.setTooltip(tooltip);

        label.setOnMouseClicked(mouseEvent -> {

            ObservableList<Node> children = getContentOverlay().getChildren();
            int size = children.size();
            Pane container = getContainerMessageBar();

            if (size > 1 && !( size==2 && children.contains(container) ) )
                if(children.get(size-1).equals(container))
                    children.remove(size-2);
                else
                    children.remove(size-1);
        });

        menuBar.getMenus().add(new Menu("", label));

        return menuBar;
    }
}
