package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static app.GestionBddApp.*;
import static javafx.application.Platform.exit;

public class MenuViewController{

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() instanceof Button clickedButton) {
            String buttonText = clickedButton.getText();

            switch (buttonText) {
                case "Afficher tous les programmeurs":
                    try{
                        Pages.showDataDisplayPage(programmeurDAO.getAll());
                    } catch (SQLException e) {
                        System.err.println(e.getMessage());
                    }
                    break;

                case "Afficher un programmeur":
                    try{
                        //TODO input id with integer check
                        Pages.showProfileData(programmeurDAO.getById(1));
                    } catch (SQLException e) {
                        logger.error(e.getMessage());
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
            rootLayout.setCenter(null);
            Pages.showMenuPage();
        });

        menu.getItems().add(returnMenuItem);
        menuBar.getMenus().add(menu);

        /* ----------- FETCH DATA BUTTON ----------- */
        // (Pour quand la BDD est modifiée [via MySQL Workbench ou DataGenerator])
        Menu refreshData = getMenu();
        menuBar.getMenus().add(refreshData);

        return menuBar;
    }

    @NotNull
    private static Menu getMenu() {
        Label label = new Label("Fetch Data");

        Tooltip tooltip = new Tooltip("Remet à jour les données affichées en synchronisant avec la BDD.");
        label.setTooltip(tooltip);

        label.setOnMouseClicked(mouseEvent->{
            try {
                Pages.showDataDisplayPage(programmeurDAO.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        return new Menu("", label);
    }
}
