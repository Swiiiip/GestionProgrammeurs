package data.api.request;

import com.fasterxml.jackson.databind.JsonNode;
import personnes.utils.Address;
import personnes.utils.Pictures;
import utils.Gender;
import utils.Title;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Classe permettant de récupérer des données d'utilisateur depuis une API en ligne.
 * Cette classe étend la classe abstraite Request et implémente des méthodes spécifiques pour extraire
 * des informations telles que le nom, le prénom, l'adresse, etc., à partir des données JSON renvoyées par
 * l'API.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class GetUserFromAPI extends Request {

    /**
     * Constructeur par défaut de la classe GetUserFromAPI.
     * Il appelle le constructeur de la classe mère Request et initialise l'objet ObjectMapper pour la désérialisation JSON.
     */
    public GetUserFromAPI() {
        super();
    }

    /**
     * Récupère le nom de famille de l'utilisateur à partir des données JSON.
     *
     * @return Le nom de famille de l'utilisateur.
     * @throws IOException En cas d'erreur lors de la lecture des données JSON.
     */
    public String getLastName() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode nameNode = rootNode.path("results").get(0).get("name");
        return nameNode.get("last").asText();
    }

    /**
     * Récupère le prénom de l'utilisateur à partir des données JSON.
     *
     * @return Le prénom de l'utilisateur.
     * @throws IOException En cas d'erreur lors de la lecture des données JSON.
     */
    public String getFirstName() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");

        return nameNode.get("first").asText();
    }

    /**
     * Récupère l'adresse de l'utilisateur à partir des données JSON.
     *
     * @return L'adresse de l'utilisateur sous forme d'objet Address.
     * @throws IOException En cas d'erreur lors de la lecture des données JSON.
     */
    public Address getAddress() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode locationNode = rootNode.get("results").get(0).get("location");
        int streetNumber = locationNode.get("street").get("number").asInt();
        String streetName = locationNode.get("street").get("name").asText();
        String city = locationNode.get("city").asText();
        String state = locationNode.get("state").asText();
        String country = locationNode.get("country").asText();
        String postcode = locationNode.get("postcode").asText();

        return new Address(streetNumber, streetName, city, state, country, postcode);
    }

    /**
     * Récupère l'année de naissance de l'utilisateur à partir des données JSON.
     *
     * @return L'année de naissance de l'utilisateur.
     * @throws IOException En cas d'erreur lors de la lecture des données JSON.
     */
    public int getBirthYear() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode birthYearNode = rootNode.get("results").get(0).get("dob");

        int age = birthYearNode.get("age").asInt();

        return LocalDate.now().getYear() - age;
    }

    /**
     * Récupère le pseudo de l'utilisateur à partir des données JSON.
     *
     * @return Le pseudo de l'utilisateur.
     * @throws IOException En cas d'erreur lors de la lecture des données JSON.
     */
    public String getPseudo() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode loginNode = rootNode.get("results").get(0).get("login");

        return loginNode.get("username").asText();
    }

    /**
     * Récupère le genre de l'utilisateur à partir des données JSON.
     *
     * @return Le genre de l'utilisateur (Masculin ou Féminin).
     * @throws IOException En cas d'erreur lors de la lecture des données JSON.
     */
    public Gender getGender() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode genderNode = rootNode.get("results").get(0).get("gender");

        return genderNode.asText().equalsIgnoreCase(Gender.MALE.getGender()) ? Gender.MALE : Gender.FEMALE;
    }

    /**
     * Récupère les images de profil de l'utilisateur à partir des données JSON.
     *
     * @return Les images de profil de l'utilisateur sous forme d'objet Pictures.
     * @throws IOException En cas d'erreur lors de la lecture des données JSON.
     */
    public Pictures getPictures() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode picturesNode = rootNode.get("results").get(0).get("picture");

        return new Pictures(picturesNode.get("large").asText(),
                picturesNode.get("medium").asText(),
                picturesNode.get("thumbnail").asText());
    }

    /**
     * Récupère le titre de civilité de l'utilisateur à partir des données JSON.
     *
     * @return Le titre de civilité de l'utilisateur (Monsieur, Madame, Mademoiselle ou Valeur par défaut).
     * @throws IOException En cas d'erreur lors de la lecture des données JSON.
     */
    public Title getTitle() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");
        String currentTitle = nameNode.get("title").asText();

        if (currentTitle.equalsIgnoreCase("mr") || currentTitle.equalsIgnoreCase("monsieur"))
            return Title.MR;

        if (currentTitle.equalsIgnoreCase("mrs") || currentTitle.equalsIgnoreCase("madame"))
            return Title.MRS;

        if (currentTitle.equalsIgnoreCase("ms") || currentTitle.equalsIgnoreCase("miss") || currentTitle.equalsIgnoreCase("mademoiselle"))
            return Title.MS;

        return Title.DEFAULT;
    }

    /**
     * Obtient l'URL de l'API à interroger.
     *
     * @return L'URL de l'API.
     */
    public String getUserapi() {
        return "https://randomuser.me/api";
    }
}
