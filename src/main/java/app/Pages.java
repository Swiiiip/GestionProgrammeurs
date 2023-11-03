package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Carousel;

import java.io.IOException;
import java.util.List;

import static app.GestionBddApp.*;

public class Pages {

    public static VBox getMenuPage() {
        try {
            primaryStage.setTitle("Gestion BDD | "+menuMode.getSimpleName()+" | Menu ");

            FXMLLoader loader = new FXMLLoader(Pages.class.getResource("Menu.fxml"));

            return loader.load();

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static VBox getDataDisplayPage(List<?> data) {
        try {
            primaryStage.setTitle("Gestion BDD | "+menuMode.getSimpleName()+" | Data Display");

            FXMLLoader loader = new FXMLLoader(DataViewController.class.getResource("DataDisplay.fxml"));
            VBox dataPageLayout = loader.load();

            DataViewController dataViewController = loader.getController();

            dataViewController.initializeTableView(data);
            dataViewController.updateTableView(data);

            return dataPageLayout;

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    public static HBox getProfileDataPage(Object data) {
        primaryStage.setTitle("Gestion BDD | "+menuMode.getSimpleName()+" | Profile View");
        Carousel carousel = new Carousel(data);

        return carousel.getCarouselLayout();
    }

    public static VBox getAddProfilePage(){
        try {
            boolean isManager = false;

            primaryStage.setTitle("Gestion BDD | "+menuMode.getSimpleName()+" | Add "+(isManager ? "Manager" : "Programmeur"));

            FXMLLoader loader = new FXMLLoader(AddProfileController.class.getResource("AddProfile.fxml"));
            VBox addProfileLayout = loader.load();

            AddProfileController addProfileController = loader.getController();
            addProfileController.initialize(isManager);

            return addProfileLayout;

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    public static VBox getGenerateProfilesPage() {
        try {
            primaryStage.setTitle("Gestion BDD | "+menuMode.getSimpleName()+" | Generate Profiles");

            FXMLLoader loader = new FXMLLoader(GenerateProfilesController.class.getResource("GenerateProfiles.fxml"));
            VBox addProfileLayout = loader.load();

            GenerateProfilesController addProfileController = loader.getController();

            return addProfileLayout;

        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }
}


