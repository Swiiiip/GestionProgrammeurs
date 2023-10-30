package data.api.request;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Classe permettant de récupérer les coordonnées géographiques (latitude et longitude) d'une ville à partir d'une API.
 * Cette classe étend la classe abstraite Request et utilise l'API Nominatim d'OpenStreetMap pour obtenir les coordonnées
 * géographiques d'une ville donnée. Elle permet de convertir le nom de la ville et du pays en UTF-8, puis d'interroger
 * l'API pour obtenir les coordonnées.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public class GetCoordsFromAPI extends Request {

    /**
     * Constructeur de la classe GetCoordsFromAPI.
     * Ce constructeur prend le nom de la ville et du pays en tant que paramètres et génère une requête
     * pour obtenir les coordonnées géographiques (latitude et longitude) de la ville.
     *
     * @param city Le nom de la ville.
     * @param country Le nom du pays.
     * @throws SecurityException En cas d'erreur lors de l'initialisation des coordonnées.
     */
    public GetCoordsFromAPI(String city, String country) {
        super();

        // Encodage en UTF-8 et suppression des espaces
        city = URLEncoder.encode(city, StandardCharsets.UTF_8);
        country = URLEncoder.encode(country, StandardCharsets.UTF_8);
        city = city.split(" ")[0];
        country = country.split(" ")[0];

        String apiurl = "https://nominatim.openstreetmap.org/search.php" +
                "?city=" + city +
                "&country=" + country +
                "&format=json" +
                "&accept-langage=xx";

        try {
            this.generateData(apiurl);
        } catch (Exception e) {
            throw new SecurityException("L'initialisation des coordonnées a échouée" + e.getMessage());
        }
    }

    /**
     * Récupère la latitude à partir des données JSON de l'API.
     *
     * @return La latitude de la ville.
     * @throws Exception En cas d'erreur lors de la lecture des données JSON ou si aucune donnée n'est disponible.
     */
    public float getLatitude() throws Exception {
        JsonNode rootNode = objectMapper.readTree(this.data);
        JsonNode firstResult;
        float latitudeAPI;
        if (rootNode.isArray() && !rootNode.isEmpty()) {
            firstResult = rootNode.get(0);
            latitudeAPI = Float.parseFloat(firstResult.get("lat").asText());
        } else {
            throw new Exception();
        }

        return this.parse(latitudeAPI);
    }

    /**
     * Récupère la longitude à partir des données JSON de l'API.
     *
     * @return La longitude de la ville.
     * @throws Exception En cas d'erreur lors de la lecture des données JSON ou si aucune donnée n'est disponible.
     */
    public float getLongitude() throws Exception {
        JsonNode rootNode = objectMapper.readTree(this.data);
        JsonNode firstResult;
        float longitudeAPI;
        if (rootNode.isArray() && !rootNode.isEmpty()) {
            firstResult = rootNode.get(0);
            longitudeAPI = Float.parseFloat(firstResult.get("lon").asText());
        } else {
            throw new Exception();
        }

        return this.parse(longitudeAPI);
    }

    /**
     * Parse la valeur de coordonnée géographique pour la formater correctement.
     *
     * @param coord La valeur de la coordonnée géographique.
     * @return La valeur de coordonnée géographique formatée.
     * @throws ParseException En cas d'erreur lors de la conversion de la valeur.
     */
    private float parse(float coord) throws ParseException {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");

        String formattedValue = decimalFormat.format(coord);

        return decimalFormat.parse(formattedValue).floatValue();
    }
}
