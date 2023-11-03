package app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import personnes.Manager;
import personnes.Personne;
import personnes.Programmeur;
import personnes.utils.Address;
import personnes.utils.Coords;
import personnes.utils.Pictures;
import utils.*;

import java.io.File;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static app.GestionBddApp.*;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.time.LocalDate.now;

public class AddProfileController {


    @FXML
    private TextField countryTextField;

    @FXML
    private TextField regionTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField streetNameTextField;

    @FXML
    private TextField streetNumberTextField;

    @FXML
    private ChoiceBox<String> managerChoiceBox;

    @FXML
    private Button addProfileButton;

    @FXML
    private TextField bonusTextField;

    @FXML
    private ChoiceBox<String> hobbyChoiceBox;

    @FXML
    public ChoiceBox<String> departmentChoiceBox;

    @FXML
    private TextField salaryTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField birthYearTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private ChoiceBox<String> titleChoiceBox;

    @FXML
    private ChoiceBox<String> genderChoiceBox;

    @FXML
    private TextField pseudonymTextField;

    @FXML
    private ImageView profilePicture;

    @FXML
    private ImageView addImageOverlay;

    private boolean isSelectedPP = false;

    @FXML
    private void handleImageOverlayClick(MouseEvent event) {
        openImageFileDialog();
    }

    private void openImageFileDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if(selectedFile != null) {
            profilePicture.setImage(new Image(selectedFile.toURI().toString()));
            isSelectedPP = true;
        }

    }

    public void initialize(boolean isManager) {
        setupChoiceBoxes();
        resetInputs(isManager);

        addProfileButton.setOnAction(event -> {
            resetInputs(isManager);
            String warningMessage = verifValues(isManager);

            Pictures pictures = new Pictures(profilePicture.getImage().getUrl(), profilePicture.getImage().getUrl(), profilePicture.getImage().getUrl());

            try {
                getProgrammeurDAO().addPictures(pictures);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            if(!warningMessage.isEmpty()) {
                MessageBar messageBar = new MessageBar();
                messageBar.displayMessageBar(warningMessage, MessageBar.MessageType.WARNING);

            }else {
                Personne personne = createIdentity(isManager);

                if(isManager) {

                    try {
                        getManagerDAO().add((Manager) personne);
                    } catch (SQLException e) {
                        MessageBar messageBar = new MessageBar();
                        messageBar.displayMessageBar("Error while adding profile to DB", MessageBar.MessageType.ERROR);
                        throw new RuntimeException(e);

                    }
                }
                else {
                    try {
                        getProgrammeurDAO().add((Programmeur) personne);
                    } catch (SQLException e) {
                        MessageBar messageBar = new MessageBar();
                        messageBar.displayMessageBar("Error while adding profile to DB", MessageBar.MessageType.ERROR);
                        throw new RuntimeException(e);
                    }
                }
                MessageBar messageBar = new MessageBar();
                messageBar.displayMessageBar("Profile added successfully !", MessageBar.MessageType.SUCCESS);
            }

        });

        setupNumberTextFields();

        genderChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!isSelectedPP) {
                logger.warn("No image selected, setting default image");
                boolean isMale = genderChoiceBox.getValue().equals("male");
                profilePicture.setImage( new Image( randomDefaultImagePicker(isMale)));
            }
        });
    }

    public void resetInputs(boolean isManager) {
        profilePicture.setImage( new Image( randomDefaultImagePicker(true)));

        if (isManager) managerChoiceBox.setDisable(true);

        firstNameTextField.setStyle("-fx-border-color: black");
        lastNameTextField.setStyle("-fx-border-color: black");
        managerChoiceBox.setStyle("-fx-border-color: black");
        pseudonymTextField.setStyle("-fx-border-color: black");
        birthYearTextField.setStyle("-fx-border-color: black");
        salaryTextField.setStyle("-fx-border-color: black");
        bonusTextField.setStyle("-fx-border-color: black");
        streetNumberTextField.setStyle("-fx-border-color: black");
        streetNameTextField.setStyle("-fx-border-color: black");
        cityTextField.setStyle("-fx-border-color: black");
        regionTextField.setStyle("-fx-border-color: black");
        countryTextField.setStyle("-fx-border-color: black");
        postalCodeTextField.setStyle("-fx-border-color: black");

    }

    public void setupChoiceBoxes() {
        titleChoiceBox.setValue("Title");
        genderChoiceBox.setValue("Gender");
        hobbyChoiceBox.setValue("Hobby");
        managerChoiceBox.setValue("Manager *");
        departmentChoiceBox.setValue("Department ");

        titleChoiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.stream(Title.values())
                                                                            .map(Title::getTitle)
                                                                            .collect(Collectors.toList())));
        genderChoiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.stream(Gender.values())
                                                                            .map(Gender::getGender)
                                                                            .collect(Collectors.toList())));
        hobbyChoiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.stream(Hobbies.values())
                                                                            .map(Hobbies::getHobby)
                                                                            .collect(Collectors.toList())));
        departmentChoiceBox.getItems().addAll(FXCollections.observableArrayList(Arrays.stream(Departments.values())
                                                                            .map(Departments::getDepartment)
                                                                            .collect(Collectors.toList())));

        try{
            List<Manager> managers = getManagerDAO().getAll();
            for (Manager manager : managers) {
                managerChoiceBox.getItems().add(manager.getFullName());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    public String verifValues(boolean isManager) {
        String warningMessage = "";

        if (lastNameTextField.getText().isEmpty()) {
            warningMessage += "Last name is required\n";
            lastNameTextField.setStyle("-fx-border-color: red");

        }if (firstNameTextField.getText().isEmpty()) {
            warningMessage += "First name is required\n";
            firstNameTextField.setStyle("-fx-border-color: red");

        } if (titleChoiceBox.getValue().equals("Title")) {
            titleChoiceBox.setValue("Unknown");

        } if (genderChoiceBox.getValue().equals("Gender")) {
            genderChoiceBox.setValue("Unknown");

        } if (managerChoiceBox.getValue().equals("Manager *") && !isManager) {
            warningMessage += "Manager is required\n";
            managerChoiceBox.setStyle("-fx-border-color: red");

        } if (pseudonymTextField.getText().isEmpty()) {
            warningMessage += "Pseudonym is required\n";
            pseudonymTextField.setStyle("-fx-border-color: red");

        } if (birthYearTextField.getText().isEmpty()) {
            warningMessage += "Birth year is required\n";
            birthYearTextField.setStyle("-fx-border-color: red");

        } else if (birthYearTextField.getText().length() != 4) {
            warningMessage += "Birth year must be 4 digits long\n";
            birthYearTextField.setStyle("-fx-border-color: red");

        } else if (parseInt(birthYearTextField.getText()) > now().getYear()) {
            warningMessage += "Birth year must be in the past\n";
            birthYearTextField.setStyle("-fx-border-color: red");

        } if (streetNumberTextField.getText().isEmpty()) {
            warningMessage += "Street number is required\n";
            streetNumberTextField.setStyle("-fx-border-color: red");

        } else if (parseInt(streetNumberTextField.getText()) < 0) {
            warningMessage += "Street number must be a positive number\n";
            streetNumberTextField.setStyle("-fx-border-color: red");

        } if (streetNameTextField.getText().isEmpty()) {
            warningMessage += "Street name is required\n";
            streetNameTextField.setStyle("-fx-border-color: red");

        } if (cityTextField.getText().isEmpty()) {
            warningMessage += "City is required\n";
            cityTextField.setStyle("-fx-border-color: red");

        } if (regionTextField.getText().isEmpty()) {
            warningMessage += "Region is required\n";
            regionTextField.setStyle("-fx-border-color: red");

        } if (countryTextField.getText().isEmpty()) {
            warningMessage += "Country is required\n";
            countryTextField.setStyle("-fx-border-color: red");

        } if (postalCodeTextField.getText().isEmpty()) {
            warningMessage += "Postal code is required\n";
            postalCodeTextField.setStyle("-fx-border-color: red");

        } if (salaryTextField.getText().isEmpty()) {
            warningMessage += "Salary is required\n";
            salaryTextField.setStyle("-fx-border-color: red");

        } else if (parseFloat(salaryTextField.getText()) < 0) {
            warningMessage += "Salary must be a positive number\n";
            salaryTextField.setStyle("-fx-border-color: red");

        } if (bonusTextField.getText().isEmpty()) {
            warningMessage += "Bonus is required\n";
            bonusTextField.setStyle("-fx-border-color: red");

        } else if (parseFloat(bonusTextField.getText()) < 0) {
            warningMessage += "Bonus must be a positive number\n";
            bonusTextField.setStyle("-fx-border-color: red");
        }

        return warningMessage;
    }

    public void setupNumberTextFields() {
        mapNumberTextField(birthYearTextField, false);
        mapNumberTextField(postalCodeTextField, false);
        mapNumberTextField(streetNumberTextField, false);

        mapNumberTextField(salaryTextField, true);
        mapNumberTextField(bonusTextField, true);
    }

    private void mapNumberTextField(TextField textField, boolean isFloat) {
        String regex = isFloat ? "^(?!.*\\.{2})\\d*\\.?\\d*$" : "^\\d*$";

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches(regex)) {
                textField.setText(oldValue);
            }
        });
    }


    public String randomDefaultImagePicker(boolean isMale) {
        int choice = (int) (Math.random() * 3 + 1);
        if (!isMale) choice += 3;

        String randomDefaultImage = "https://www.w3schools.com/w3css/img_avatar"+choice+".png";

        return randomDefaultImage;
    }

    public Personne createIdentity(boolean isManager) {
        try {
            Title title = Title.getTitleFromString(titleChoiceBox.getValue());
            String firstName = firstNameTextField.getText();
            String lastName = lastNameTextField.getText();
            String pseudo = pseudonymTextField.getText();
            Gender gender = Gender.getGenderFromString(genderChoiceBox.getValue());
            Pictures pictures = new Pictures(profilePicture.getImage().getUrl(), profilePicture.getImage().getUrl(), profilePicture.getImage().getUrl());
            Address address = new Address(parseInt(streetNumberTextField.getText()), streetNameTextField.getText(), cityTextField.getText(), regionTextField.getText(), countryTextField.getText(), postalCodeTextField.getText());
            Coords coords = new Coords(cityTextField.getText(), countryTextField.getText());
            Hobbies hobby = Hobbies.getHobbyFromString(hobbyChoiceBox.getValue());
            int birthYear = parseInt(birthYearTextField.getText());
            float salary = parseFloat(salaryTextField.getText());
            float prime = parseFloat(bonusTextField.getText());
            Departments department = Departments.getDepartmentFromString(departmentChoiceBox.getValue());
            getManagerDAO().addPictures(pictures);
            getManagerDAO().addCoords(coords);
            getManagerDAO().addAddress(address);

            pictures = getManagerDAO().getPictures(pictures);
            coords = getManagerDAO().getCoords(coords);
            address = getManagerDAO().getAddress(address);

            if(isManager)
                return new Manager(title, lastName, firstName, gender, pictures, address, coords, hobby, birthYear, salary, prime, department);
            else {
                String[] managerFullName = managerChoiceBox.getValue().split(" ");
                try{
                    Manager manager = getManagerDAO().getByFullName(managerFullName[0], managerFullName[1]);

                    return new Programmeur(title, lastName, firstName, gender, pictures, address, coords, pseudo, manager, hobby, birthYear, salary, prime);
                }catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

}
