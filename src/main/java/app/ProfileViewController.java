package app;

import javafx.fxml.FXML;

import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import personnes.Manager;
import personnes.Personne;

import javafx.scene.layout.VBox;
import utils.Coords;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ProfileViewController {

    @FXML
    private ImageView profilePictureImageView;

    @FXML
    private Text fullNameText;

    @FXML
    private Text roleText;

    @FXML
    private Text profileId;

    @FXML
    private VBox profileContent;

    public void initialize(Object data) {
        System.out.println("Profile data : " + data);

        setupProfileHeader(data);
        setupProfileContent(data);
        //TODO tmp setups ^, these are the building blocks for each profile view
        //maybe we'll be able to implement the carousel effect after that 👀
    }

    public void setupProfileHeader(Object data){
        String pictureLink, fullName;
        int id = -1;

        if(data instanceof Personne p){
            pictureLink = p.getPictures().getLarge();

            String title = p.getTitle();
            fullName = title + " " + p.getFullName();

            id = p.getId();
        }else{
            pictureLink = "https://www.w3schools.com/howto/img_avatar.png";
            fullName = "NOM Prénom";
        }

        fullNameText.setText( fullName );

        roleText.setText( data.getClass().getSimpleName() );

        profileId.setText( "ID " + id );

        profilePictureImageView.setImage(new Image(pictureLink));

        double radius = 50;
        Circle clip = new Circle(radius, radius, radius);
        profilePictureImageView.setClip(clip);

        profilePictureImageView.setFitWidth(2 * radius);
        profilePictureImageView.setFitHeight(2 * radius);
    }

    public void setupProfileContent(Object data){
        List<Text> textLabels = createTextLabelsForAttributes(data);
        profileContent.getChildren().addAll(textLabels);

        if(data instanceof Personne p){
            WebView mapView = createMapView(p.getCoords());
            profileContent.getChildren().add(mapView);
        }
    }

    public List<Text> createTextLabelsForAttributes(Object data) {
        List<Text> textLabels = new ArrayList<>();
        String fieldName, fieldValue;

        Class<?> clazz = data.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                fieldName = field.getName();

                if(fieldName.equals("pictures") || fieldName.equals("id") || fieldName.equals("fullName") || fieldName.equals("firstName") || fieldName.equals("lastName") || fieldName.equals("title") || fieldName.equals("coords"))
                    continue;

                if(fieldName.equals("manager")) {
                    textLabels.add( createManagerTextLabel(field, data) );
                    continue;
                }

                try{
                    fieldValue = field.get(data).toString();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                Text textLabel = new Text(fieldName + " : " + fieldValue);
                textLabels.add(textLabel);
            }

            clazz = clazz.getSuperclass();
        }

        return textLabels;
    }

    private Text createManagerTextLabel( @NotNull Field field, Object data) {
        Manager managerData;
        String fieldValue = "NaN";

        try {
            managerData = (Manager) field.get(data);
            fieldValue = managerData.getFullName();

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Text textLabel = new Text("manager : " + fieldValue);
        textLabel.setFill(Color.BLUE);

        textLabel.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Pages.showProfileData(managerData);
            }
        });

        return textLabel;
    }

    public WebView createMapView(Coords coords){
        WebView webView = new WebView();
        double size = 400;
        webView.setMinSize(size, size);
        webView.setMaxSize(size, size);

        WebEngine webEngine = webView.getEngine();

        double latitude, longitude;

        try{
            latitude = Double.parseDouble(coords.getLatitude());
            longitude = Double.parseDouble(coords.getLongitude());
        } catch (Exception e) {
            System.err.println("Aucune coordonnée fournie");
            webEngine.load("<h1>Aucune coordonnée fournie</h1>");
            return webView;
        }

        int latitudeInt = (int) latitude;
        int longitudeInt = (int) longitude;
        int viewRange = 10;

        String html = "https://www.openstreetmap.org/export/embed.html?bbox=" + (longitudeInt - viewRange) + "," + (latitudeInt - viewRange) + "," + (longitudeInt + viewRange) + "," + (latitudeInt + viewRange) + "&marker=" + latitudeInt + "," + longitudeInt + "&layers=ND";
        webEngine.load(html);

        return webView;
    }
}
