package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

import static app.GestionBddApp.*;

public class Pages {

    public static VBox getMenuPage() {
            try {
            primaryStage.setTitle("Gestion BDD | Menu");

            FXMLLoader loader = new FXMLLoader(Pages.class.getResource("Menu.fxml"));

            return loader.load();

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static VBox getDataDisplayPage(List<?> data) {
        try {
            primaryStage.setTitle("Gestion BDD | Data Display");

            FXMLLoader loader = new FXMLLoader(DataViewController.class.getResource("DataDisplay.fxml"));
            VBox dataPageLayout = loader.load();

            DataViewController dataViewController = loader.getController();

            dataViewController.initializeTableView(data);
            dataViewController.updateTableView(data);

            //dataPageLayout.getChildren().add( [add in here VBox to split the window in half and add statistics if needed !] );

            return dataPageLayout;

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static VBox getProfileData(Object data){
        try{
            primaryStage.setTitle("Gestion BDD | Data Display");

            FXMLLoader loader = new FXMLLoader(ProfileViewController.class.getResource("ProfileView.fxml"));
            VBox profileLayout = loader.load();

            ProfileViewController profileViewController = loader.getController();
            profileViewController.initialize(data);

            return profileLayout;

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }

    }
}


