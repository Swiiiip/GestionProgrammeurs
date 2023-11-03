package utils;

import app.ProfileViewController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;

import static app.GestionBddApp.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Carousel {
    private StackPane carousel;
    private int currentIndex = 0;
    private int numItems;
    private List<?> items;
    private Timeline animation;

    public Carousel(Object data) {
        if (!checkDataType(data)) {
            logger.error("Carousel: data type not supported");
            return;
        }

        setItems(data);
        carousel = createCarousel();

        showItem(getIndexFromId(data));
    }

    private void setItems(Object data) {
        try {
            if (data instanceof Programmeur) {
                items = getProgrammeurDAO().getAll();
            } else if (data instanceof Manager) {
                items = getManagerDAO().getAll();
            } else {
                throw new IllegalArgumentException("Type de données non reconnu");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    public HBox getCarouselLayout() {
        Button prevButton = new Button("Previous");
        Button nextButton = new Button("Next");

        prevButton.setOnAction(e -> showPreviousItem());
        nextButton.setOnAction(e -> showNextItem());

        HBox carouselLayout = new HBox(prevButton, carousel, nextButton);
        carouselLayout.getStyleClass().add("carousel");

        return carouselLayout;
    }

    private int getIndexFromId(Object data) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof Personne p && p.getId() == ((Personne) items.get(i)).getId()) {
                return i;
            }
        }
        return -1;
    }

    private boolean checkDataType(Object data) {
        return data instanceof Personne;
    }

    public ProfileViewController createProfileViewController() {
        try {
            FXMLLoader loader = new FXMLLoader(ProfileViewController.class.getResource("ProfileView.fxml"));
            VBox profileLayout = loader.load();
            ProfileViewController profileViewController = loader.getController();

            profileViewController.setProfileBlock(profileLayout);

            return profileViewController;
        } catch (IOException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private StackPane createCarousel() {
        numItems = items.size();
        carousel = new StackPane();

        double verticalSpacing = 10.0;

        for (int i = 0; i < numItems; i++) {
            ProfileViewController profileViewController = createProfileViewController();
            VBox profileBlock = profileViewController.createProfileBlock(items.get(i));

            // Calculate the vertical position for the current profile block
            double translateY = i * (146.0 + verticalSpacing);

            // Set the vertical position of the profile block
            profileBlock.setTranslateY(translateY);

            // Add profile blocks to the VBox
            carousel.getChildren().add(profileBlock);
        }

        return carousel;
    }

    private void showPreviousItem() {
        carousel.setMouseTransparent(true); //TODO tmp

        int newIndex = (currentIndex - 1 + numItems) % numItems;
        showItem(newIndex);
    }

    private void showNextItem() {
        carousel.setMouseTransparent(false); //TODO tmp

        int newIndex = (currentIndex + 1) % numItems;
        showItem(newIndex);
    }

    private void showItem(int index) {
        System.out.println("--> NEW showItem");

        closeAllTitledPanes();

        ObservableList<Node> profileBlocks = carousel.getChildren();

        VBox profileBlock = (VBox) profileBlocks.get(currentIndex);
        Node titledPane = profileBlock.getChildren().get(1);

        profileBlock.toBack();
        //profileBlock.setStyle("-fx-opacity: 0.5;");

        ((TitledPane) titledPane).setCollapsible(false);

        List<Boolean> listMatchingVBox = profileBlocks.stream().map(profileBox -> profileBox.getId() == profileBlocks.get(index).getId()).collect(Collectors.toList());

        /*TODO :
        A ESSAYER :
        creer une liste qui stocker a la position initiale des vbox QUI SERA JAMAIS MODIFIEE
        le reste marche normalement : update les VBox issu du carousel attribute directement (toFront etc) PAS A LA LISTE D AVANT^
        condition pour verif c en faisant un equals ou jsp quoi, pour voir si c le meme objet
        directe applique toFront a l objet vbox ds carousel.items qd cette condition est verif
         */

        for(int i = 0; i < profileBlocks.size(); i++) {
            profileBlock = (VBox) profileBlocks.get(index);
            titledPane = profileBlock.getChildren().get(1);

            System.out.println(carousel.getChildren());

            System.out.println("\ni "+i+" "+profileBlock);
            System.out.println("index "+index+" "+profileBlocks.get(index).getId());
            System.out.println("current "+currentIndex+" "+profileBlocks.get(currentIndex).getId());
            System.out.println(index == i);

            //print a list of bool which is true when the current profileBox is the one we are evaluating, and false otherwise.
            // THis must be done without the use of index and i, but with the use of the id of the profileBox.
            System.out.println(profileBlocks.stream().map(profileBox -> profileBox.getId() == profileBlocks.get(index).getId()).collect(Collectors.toList()));

            if (listMatchingVBox.get(i)){
                profileBlocks.get(index).toFront();
                profileBlocks.get(index).setStyle("-fx-opacity: 1.0;");

                ((TitledPane) titledPane).setCollapsible(true);
            } else {
                profileBlocks.get(index).toBack();
                //profileBlocks.get(index).setStyle("-fx-opacity: 0.5;");

                //((TitledPane) titledPane).setCollapsible(false);
            }
        }

        if (animation != null) {
            animation.stop();
        }

        double endY = -index * getProfileBlockHeightWithSpacing(index);

        Duration duration = Duration.seconds(1);

        animation = new Timeline(
                new KeyFrame(duration, new KeyValue(carousel.translateYProperty(), endY))
        );

        animation.play();
        currentIndex = index;
    }

    private double getProfileBlockHeightWithSpacing(int index) {
        double spacing = 20.0; // Adjust the spacing value as needed
        VBox profileBlock = (VBox) carousel.getChildren().get(index);
        double blockHeight = profileBlock.getHeight();
        return blockHeight + spacing;
    }


    public void closeAllTitledPanes() {
        for (int i = 0; i < carousel.getChildren().size(); i++) {
            VBox profileBlock = (VBox) carousel.getChildren().get(i);
            Node titledPane = profileBlock.getChildren().get(1);

            if (titledPane instanceof TitledPane) {
                ((TitledPane) titledPane).setExpanded(false);
            }
        }
    }

}
