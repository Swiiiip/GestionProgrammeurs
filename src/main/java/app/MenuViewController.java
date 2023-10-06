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
                        displayError(e.getMessage());
                    }
                    break;

                case "Afficher un programmeur":
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
            pages.showMenuPage();
        });

        menu.getItems().add(returnMenuItem);
        menuBar.getMenus().add(menu);

        /* ----------- REFRESH DATA BUTTON ----------- */
        // (Pour quand la BDD est modifiée [via MySQL Workbench ou DataGenerator])
        Label label = new Label("Refresh Data");
        label.setOnMouseClicked(mouseEvent->{
            try {
                pages.showDataDisplayPage(programmeurDAO.getAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        Menu refreshData = new Menu("", label);
        menuBar.getMenus().add(refreshData);

        return menuBar;
    }
}
