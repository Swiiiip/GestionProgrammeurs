package app;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class Pages extends GestionBddApp {

    public static void showMenuPage() {
        try {
            primaryStage.setTitle("Gestion BDD | Menu");

            FXMLLoader loader = new FXMLLoader(Pages.class.getResource("Menu.fxml"));
            VBox menuLayout = loader.load();

            rootLayout.setCenter(menuLayout);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showDataDisplayPage(List<?> data) {
        try {
            primaryStage.setTitle("Gestion BDD | Data Display");

            FXMLLoader loader = new FXMLLoader(DataViewController.class.getResource("DataDisplay.fxml"));
            VBox dataPageLayout = loader.load();

            dataViewController = loader.getController();

            dataViewController.initializeTableView(data);
            dataViewController.updateTableView(data);

            //dataPageLayout.getChildren().add( [add in here VBox to split the window in half and add statistics if needed !] );

            rootLayout.setCenter(dataPageLayout);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showProfileData(Object data){
        try{
            System.out.println("showSpecificData : "+data);
            primaryStage.setTitle("Gestion BDD | Data Display");

            FXMLLoader loader = new FXMLLoader(ProfileViewController.class.getResource("ProfileView.fxml"));
            VBox profileLayout = loader.load();

            profileViewController = loader.getController();
            profileViewController.initialize(data);
            rootLayout.setCenter(profileLayout);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


