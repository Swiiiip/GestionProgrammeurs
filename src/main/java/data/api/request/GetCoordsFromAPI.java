package data.api.request;

import com.fasterxml.jackson.databind.JsonNode;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;

public class GetCoordsFromAPI extends Request{

    public GetCoordsFromAPI(String city, String country) {
        super();
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

    public float getLatitude() throws Exception{
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

    public float getLongitude() throws Exception{
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

    private float parse(float coord) throws ParseException {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");

        String formattedValue = decimalFormat.format(coord);

        return decimalFormat.parse(formattedValue).floatValue();
    }

}
