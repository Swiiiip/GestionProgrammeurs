package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import static javafx.application.Platform.exit;

public class DataViewController {
    private GestionBddApp mainApp;

    public void setMainApp(GestionBddApp mainApp) {
        this.mainApp = mainApp;
    }

}
