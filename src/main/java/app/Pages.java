package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

import static app.GestionBddApp.primaryStage;
import static app.GestionBddApp.rootLayout;

public class Pages {

    public static void showMenuPage() {
        try {
            primaryStage.setTitle("Gestion BDD | Menu");

            FXMLLoader loader = new FXMLLoader(Pages.class.getResource("Menu.fxml"));
            VBox menuLayout = loader.load();

            rootLayout.setCenter(menuLayout);

        } catch (IOException e) {
            System.err.println(e.getCause());
        }
    }

    public static void showDataDisplayPage(List<?> data) {
        try {
            primaryStage.setTitle("Gestion BDD | Data Display");

            FXMLLoader loader = new FXMLLoader(DataViewController.class.getResource("DataDisplay.fxml"));
            VBox dataPageLayout = loader.load();

            DataViewController dataViewController = loader.getController();

            dataViewController.initializeTableView(data);
            dataViewController.updateTableView(data);

            //dataPageLayout.getChildren().add( [add in here VBox to split the window in half and add statistics if needed !] );

            rootLayout.setCenter(dataPageLayout);

        } catch (IOException e) {
            System.err.println(e.getCause());
        }
    }

    public static void showProfileData(Object data){
        try{
            primaryStage.setTitle("Gestion BDD | Data Display");

            FXMLLoader loader = new FXMLLoader(ProfileViewController.class.getResource("ProfileView.fxml"));
            VBox profileLayout = loader.load();

            ProfileViewController profileViewController = loader.getController();
            profileViewController.initialize(data);
            rootLayout.setCenter(profileLayout);

        } catch (IOException e) {
            System.err.println(e.getCause());
        }

    }
}


