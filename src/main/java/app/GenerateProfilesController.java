package app;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import utils.MessageBar;

public class GenerateProfilesController {

    @FXML
    private TextField managersTextField;

    @FXML
    private TextField programmersTextField;

    @FXML
    private void submitButtonClicked() {
        try {
            int numManagers = Integer.parseInt(managersTextField.getText());
            int numProgrammers = Integer.parseInt(programmersTextField.getText());

            if (numManagers < 0 || numProgrammers < 0) {
                MessageBar messageBar = new MessageBar();

            } else {
                showAlert("Number of Managers: " + numManagers + "\nNumber of Programmers: " + numProgrammers);
            }
        } catch (NumberFormatException e) {
            showAlert("Please enter valid integers for both fields.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait().filter(response -> response == ButtonType.OK);
    }
}
