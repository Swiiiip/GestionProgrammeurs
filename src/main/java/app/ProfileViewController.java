package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import personnes.Programmeur;

import static app.GestionBddApp.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class ProfileViewController {
    @FXML
    private ImageView profilePictureImageView;

    @FXML
    private Text fullNameText;

    @FXML
    private Text genderText;

    @FXML
    private Text addressText;

    @FXML
    private Text pseudoText;

    @FXML
    private Text managerText;

    @FXML
    private Text hobbyText;

    @FXML
    private Text birthYearText;

    @FXML
    private Text salaryText;

    @FXML
    private Text primeText;

    public void initialize(Object data) {

        System.out.println("Profile data : " + data);

        /* //TODO
            - MAKE A SEPARATE METHOD FUNCTION FOR EACH IDEA V

            - set all the fields by completing the while loop above so that it generates fields for any given object, with diff attributes/fields
            - find a way to do getAll for any given object (maybe use a switch case with the class name ?)
            - fill the fields with the data from the object
            - display the PP in the top left corner, full name and type of object (programmeur OU manager) of person in the top right corner
            - then display the rest of generic fields right
            - add a back button to go back to the previous page
         */



    }
}
