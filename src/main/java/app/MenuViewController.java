package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

import static app.GestionBddApp.*;
import static exec.AppliManagement.displayError;
import static javafx.application.Platform.exit;

public class MenuViewController{

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button clickedButton = (Button) event.getSource();
            String buttonText = clickedButton.getText();

            switch (buttonText) {
                case "Afficher tous les programmeurs":
                    try{
                        Pages.showDataDisplayPage(programmeurDAO.getAll());
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;

                case "Afficher un programmeur":
                    try{
                        Pages.showProfileData(programmeurDAO.getById(1));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                    break;

                case "Supprimer un programmeur":
                    break;

                case "Ajouter un programmeur":
                    break;

                case "Modifier le salaire":
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

        Menu refreshData = new Menu("", label);
        menuBar.getMenus().add(refreshData);

        return menuBar;
    }
}
