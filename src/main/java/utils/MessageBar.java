package utils;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import static app.GestionBddApp.getContainerMessageBar;
import static app.GestionBddApp.getContentOverlay;

public class MessageBar extends StackPane {

    private static final double MESSAGE_DISPLAY_TIME = 2.0;
    private final Label messageLabel = new Label();
    public Pane containerPane = new Pane();

    public MessageBar() {
        containerPane.setMouseTransparent(true);

        HBox messageBox = new HBox(messageLabel);
        messageBox.setAlignment(Pos.CENTER);

        messageBox.getStyleClass().add("message-box");
        messageLabel.getStyleClass().add("message-label");
        this.getStyleClass().add("message-bar");

        getChildren().addAll(messageBox);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(translateYProperty(), getHeight())),
                new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(translateYProperty(), 0)),
                new KeyFrame(Duration.seconds(MESSAGE_DISPLAY_TIME),
                        new KeyValue(translateYProperty(), 0)),
                new KeyFrame(Duration.seconds(MESSAGE_DISPLAY_TIME + 0.3),
                        new KeyValue(translateYProperty(), getHeight()))
        );

        timeline.setOnFinished(event -> hideMessage());
        setOnMouseClicked(event -> hideMessage());
    }

    public void displayMessageBar(String text, MessageType messageType) {
        setUpMessage(text, messageType);

        getContainerMessageBar().getChildren().add(this);
        getContentOverlay().getChildren().add(getContainerMessageBar());
        setAlignment(getContainerMessageBar(), Pos.BOTTOM_CENTER); //TODO PROB MINEUR : messageBar mal positionné, cette ligne fait rien :(
    }

    public void setUpMessage(String text, MessageType messageType) {
        messageLabel.setText(text);

        switch (messageType) {
            case SUCCESS:
                getStyleClass().setAll("message-bar", "success");
                messageLabel.getStyleClass().setAll("message-label", "success");
                break;
            case ERROR:
                getStyleClass().setAll("message-bar", "error");
                messageLabel.getStyleClass().setAll("message-label", "error");
                break;
            case WARNING:
                getStyleClass().setAll("message-bar", "warning");
                messageLabel.getStyleClass().setAll("message-label", "warning");
                break;
        }

        Timeline startTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(translateYProperty(), getHeight())),
                new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(translateYProperty(), 0))
        );
        startTimeline.playFromStart();

        Timeline hideTimeline = new Timeline(
                new KeyFrame(Duration.seconds(MESSAGE_DISPLAY_TIME),
                        event -> hideMessage())
        );
        hideTimeline.playFromStart();

        //logger.info("BEFORE Current containerPane : " + getContainerMessageBar() + " | " +getContentOverlay().getChildren().toString() + " " + getContentOverlay().getChildren().size());
        getContentOverlay().getChildren().remove(getContainerMessageBar());
    }

    private void hideMessage() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(translateYProperty(), 0)),
                new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(translateYProperty(), getHeight()))
        );

        timeline.setOnFinished(event -> setVisible(false));
        timeline.playFromStart();
    }

    public enum MessageType {
        SUCCESS,
        ERROR,
        WARNING
    }
}
