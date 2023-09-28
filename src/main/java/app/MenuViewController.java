package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import static javafx.application.Platform.exit;


public class MenuViewController{
    private GestionBddApp mainApp;

    public void setMainApp(GestionBddApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML private Button button1;
    @FXML private Button button2;
    @FXML private Button button3;
    @FXML private Button button4;
    @FXML private Button button5;
    @FXML private Button button6;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() instanceof Button) {
            Button clickedButton = (Button) event.getSource();

            // Determine which button was clicked by its text
            String buttonText = clickedButton.getText();

            // Implement logic based on the button's text
            switch (buttonText) {
                case "Afficher tous les programmeurs":
                    // TODO : Switch a la page pour la liste des programmeurs
                    Pages.showDataDisplayPage();
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
                    System.err.println("L'evenement n'est pas reconnu");
                    break;
            }
        }
    }

    //TODO : A completer ofc
}
