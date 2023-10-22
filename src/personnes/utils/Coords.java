package personnes.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.ParseException;

@JsonPropertyOrder({"latitude", "longitude"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Coords {

    private int id;
    private Float latitude;
    private Float longitude;

    public Coords() {
        this.latitude = null;
        this.longitude = null;
    }

    public Coords(String city, String country){
        city = URLEncoder.encode(city, StandardCharsets.UTF_8);
        country = URLEncoder.encode(country, StandardCharsets.UTF_8);
        city = city.split(" ")[0];
        country = country.split(" ")[0];
        String data;
        try {
            data = this.init(city, country);
        } catch (Exception e){
            throw new SecurityException("L'initialisation des coordonnées a échouée" + e.getMessage());
        }
        try {
            this.setLatitude(data);
            this.setLongitude(data);
        } catch (Exception e) {
            this.latitude = 48.7893f;
            this.longitude = 2.3637f;
        }
    }

    private String init(String city, String country) throws Exception{
        String apiurl = "https://nominatim.openstreetmap.org/search.php" +
                "?city=" + city +
                "&country=" + country +
                "&format=json" +
                "&accept-langage=xx";


        URI uri = new URI(apiurl);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        conn.disconnect();
        return response.toString();
    }

    private void setLatitude(String data) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode firstResult;
        float latitudeAPI;
        if (rootNode.isArray() && !rootNode.isEmpty()) {
            firstResult = rootNode.get(0);
            latitudeAPI = Float.parseFloat(firstResult.get("lat").asText());
        } else {
            throw new Exception();
        }

        this.latitude = this.parse(latitudeAPI);
    }

    private void setLongitude(String data) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode firstResult;
        float longitudeAPI;
        if (rootNode.isArray() && !rootNode.isEmpty()) {
            firstResult = rootNode.get(0);
            longitudeAPI = Float.parseFloat(firstResult.get("lon").asText());
        } else {
            throw new Exception();
        }

        this.longitude = this.parse(longitudeAPI);
    }

    private float parse(float coord) throws ParseException {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");

        String formattedValue = decimalFormat.format(coord);

        return decimalFormat.parse(formattedValue).floatValue();
    }

    @JsonIgnore
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("latitude")
    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }


    @Override
    public String toString() {
        return "->latitude : " + this.latitude + ", longitude : " + this.longitude;
    }
}
