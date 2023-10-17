package app;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.jetbrains.annotations.NotNull;
import personnes.Manager;
import personnes.Personne;
import personnes.utils.Coords;
import utils.Title;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static app.GestionBddApp.getContentOverlay;
import static app.GestionBddApp.logger;

public class ProfileViewController {

    @FXML
    private VBox profileBlock;

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
        logger.info("Profile data : " + data);

        setupProfileHeader(data);
        setupProfileContent(data);
        //TODO tmp setups ^, these are the building blocks for each profile view
        //maybe we'll be able to implement the carousel effect after that 👀
    }

    public void setupProfileHeader(Object data) {
        String pictureLink, fullName;
        int id = -1;

        if (data instanceof Personne p) {
            pictureLink = p.getPictures().getLarge();


            fullName = p.getTitle() + " " + p.getFullName();

            id = p.getId();
        } else {
            pictureLink = "https://www.w3schools.com/howto/img_avatar.png";
            fullName = "NOM Prénom";
        }

        fullNameText.setText(fullName);

        roleText.setText(data.getClass().getSimpleName());

        profileId.setText("ID " + id);

        profilePictureImageView.setImage(new Image(pictureLink));

        double radius = 50;
        Circle clip = new Circle(radius, radius, radius);
        profilePictureImageView.setClip(clip);

        profilePictureImageView.setFitWidth(2 * radius);
        profilePictureImageView.setFitHeight(2 * radius);
    }

    public void setupProfileContent(Object data) {
        List<TextFlow> textLabels = createTextLabelsForAttributes(data);
        profileContent.getChildren().addAll(textLabels);

        if (data instanceof Personne p) {
            WebView mapView = createMapView(p.getCoords());
            profileContent.getChildren().add(mapView);
        }
    }

    public List<TextFlow> createTextLabelsForAttributes(Object data) {
        List<TextFlow> textFlows = new ArrayList<>();
        String fieldName, fieldValue;

        Class<?> clazz = data.getClass();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);

                fieldName = field.getName();

                if (fieldName.equals("pictures") || fieldName.equals("id") || fieldName.equals("fullName") || fieldName.equals("firstName") || fieldName.equals("lastName") || fieldName.equals("title") || fieldName.equals("coords"))
                    continue;

                if (fieldName.equals("manager")) {
                    textFlows.add(createManagerTextLabel(field, data));
                    continue;
                }

                try {
                    fieldValue = field.get(data).toString();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }

                if (fieldName.equals("birthYear") && data instanceof Personne p) {
                    fieldValue = fieldValue + " (" + p.getAge() + " ans)";
                }

                Text textLabel = new Text(fieldName + " : " + fieldValue);
                textFlows.add(new TextFlow(textLabel));
            }

            clazz = clazz.getSuperclass();
        }

        reorderTextFlows(textFlows, List.of("pseudo", "gender", "hobby", "birthYear", "salary", "prime", "manager", "department", "address"));

        return textFlows;
    }

    public void reorderTextFlows(List<TextFlow> textFlows, List<String> listOrderFields) {
        Map<String, TextFlow> textFlowMap = new LinkedHashMap<>();

        for (TextFlow textFlow : textFlows) {
            String text = getTextFromTextFlow(textFlow);
            textFlowMap.put(text, textFlow);
        }

        List<TextFlow> reorderedTextFlows = new ArrayList<>();

        for (String fieldName : listOrderFields) {
            TextFlow textFlow = textFlowMap.get(fieldName);
            if (textFlow != null) {
                reorderedTextFlows.add(textFlow);
                textFlowMap.remove(fieldName);
            }
        }

        reorderedTextFlows.addAll(textFlowMap.values());

        textFlows.clear();
        textFlows.addAll(reorderedTextFlows);
    }

    private String getTextFromTextFlow(TextFlow textFlow) {
        StringBuilder builder = new StringBuilder();
        for (Node text : textFlow.getChildren()) {
            try {
                if (text instanceof Text) {
                    String fieldName = ((Text) text).getText().split(" : ")[0];
                    builder.append(fieldName);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return builder.toString();
    }

    private TextFlow createManagerTextLabel(@NotNull Field field, Object data) {
        Manager managerData;
        String fieldValue;

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
                VBox box = Pages.getProfileData(managerData);
                getContentOverlay().getChildren().add(box);
            }
        });

        return new TextFlow(textLabel);
    }

    public WebView createMapView(Coords coords) {
        WebView webView = new WebView();
        double size = 400;
        webView.setMinSize(size, size);
        webView.setMaxSize(size, size);

        WebEngine webEngine = webView.getEngine();

        double latitude, longitude;

        try {
            latitude = coords.getLatitude();
            longitude = coords.getLongitude();

            if (latitude == 0.0f && longitude == 0.0f)
                throw new Exception();
        } catch (Exception e) {
            System.err.println("Aucune coordonnée fournie");
            webEngine.load("<h1>Aucune coordonnée fournie</h1>");
            return webView;
        }

        int latitudeInt = (int) latitude;
        int longitudeInt = (int) longitude;
        int viewRange = 5;

        String html = "https://www.openstreetmap.org/export/embed.html?bbox=" + (longitudeInt - viewRange) + "," + (latitudeInt - viewRange) + "," + (longitudeInt + viewRange) + "," + (latitudeInt + viewRange) + "&marker=" + latitudeInt + "," + longitudeInt + "&layers=ND";
        webEngine.load(html);

        return webView;
    }
}
