package data.api.request;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import personnes.utils.Coords;
import personnes.utils.Pictures;
import utils.Gender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;

public class RequestApi {

    private static final String APIURL = "https://randomuser.me/api";
    private final ObjectMapper objectMapper;

    private String data;

    public RequestApi() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void getJsonDataFromApi() throws Exception {
        URI uri = new URI(APIURL);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            this.data = response.toString();
        }
    }

    public String parseLastNameFromJson() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode nameNode = rootNode.path("results").get(0).path("name");
        return nameNode.path("last").asText();
    }

    public String parseFirstNameFromJson() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");

        return nameNode.get("first").asText();
    }

    public String parseAddressFromJson() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode locationNode = rootNode.get("results").get(0).get("location");
        int streetNumber = locationNode.get("street").get("number").asInt();
        String streetName = locationNode.get("street").get("name").asText();
        String city = locationNode.get("city").asText();
        String country = locationNode.get("country").asText();

        return streetNumber + " " + streetName + ", " + city + ", " + country;
    }

    public int parseBirthYearFromJson() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode birthYearNode = rootNode.get("results").get(0).get("dob");

        int age = birthYearNode.get("age").asInt();

        return LocalDate.now().getYear() - age;
    }

    public String parsePseudoFromJson() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode loginNode = rootNode.get("results").get(0).get("login");

        return loginNode.get("username").asText();
    }

    public Gender parseGenderFromJson() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode genderNode = rootNode.get("results").get(0).get("gender");

        return genderNode.asText().equalsIgnoreCase(Gender.MALE.getGender()) ? Gender.MALE : Gender.FEMALE;
    }

    public Pictures parsePicturesFromJson() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode picturesNode = rootNode.get("results").get(0).get("picture");

        return new Pictures(picturesNode.get("large").asText(),
                picturesNode.get("medium").asText(),
                picturesNode.get("thumbnail").asText());
    }

    public Coords parseCoordsFromJson() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode locationNode = rootNode.get("results").get(0).get("location");
        JsonNode coordsNode = locationNode.get("coordinates");

        String latitude = coordsNode.get("latitude").asText();
        String longitude = coordsNode.get("longitude").asText();

        return new Coords(latitude, longitude);
    }


    public String parseTitleFromJson() throws IOException {
        JsonNode rootNode = objectMapper.readTree(data);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");

        return nameNode.get("title").asText();
    }
}
