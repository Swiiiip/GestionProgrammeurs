package exec;

import dao.ProgrammeurDAO;
import data.DataGenerator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import personnes.Programmeur;

import java.sql.SQLException;

public class AfficherImageJPG extends Application {

    @Override
    public void start(Stage primaryStage) {
        new DataGenerator(5,2);

        ProgrammeurDAO prog = new ProgrammeurDAO();
        Programmeur p;
        try {
            p = prog.getById(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(p);

        ImageView imageView = new ImageView();

        Image image = new Image(p.getPicture());

        imageView.setImage(image);

        StackPane root = new StackPane();
        root.getChildren().add(imageView);

        // Créez une scène JavaFX
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Afficher une image JPG avec JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

