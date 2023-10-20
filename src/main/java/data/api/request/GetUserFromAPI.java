package data.api.request;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import personnes.utils.Address;
import personnes.utils.Pictures;
import utils.Gender;
import utils.Title;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;

public class GetUserFromAPI {

    private static final String USERAPI = "https://randomuser.me/api";

    private static final String MAPSAPI = "https://nominatim.openstreetmap.org/search?format=json&q=";
    private final ObjectMapper objectMapper;

    private String userData;

    public GetUserFromAPI() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public void generateData() throws Exception {
        URI uri = new URI(USERAPI);
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
        this.userData = response.toString();
    }

    public String getLastName() throws IOException {
        JsonNode rootNode = objectMapper.readTree(userData);
        JsonNode nameNode = rootNode.path("results").get(0).get("name");
        return nameNode.get("last").asText();
    }

    public String getFirstName() throws IOException {
        JsonNode rootNode = objectMapper.readTree(userData);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");

        return nameNode.get("first").asText();
    }

    public Address getAddress() throws IOException {
        JsonNode rootNode = objectMapper.readTree(userData);
        JsonNode locationNode = rootNode.get("results").get(0).get("location");
        int streetNumber = locationNode.get("street").get("number").asInt();
        String streetName = locationNode.get("street").get("name").asText();
        String city = locationNode.get("city").asText();
        String state = locationNode.get("state").asText();
        String country = locationNode.get("country").asText();
        String postcode = locationNode.get("postcode").asText();

        return new Address(streetNumber, streetName, city, state, country, postcode);
    }

    public int getBirthYear() throws IOException {
        JsonNode rootNode = objectMapper.readTree(userData);
        JsonNode birthYearNode = rootNode.get("results").get(0).get("dob");

        int age = birthYearNode.get("age").asInt();

        return LocalDate.now().getYear() - age;
    }

    public String getPseudo() throws IOException {
        JsonNode rootNode = objectMapper.readTree(userData);
        JsonNode loginNode = rootNode.get("results").get(0).get("login");

        return loginNode.get("username").asText();
    }

    public Gender getGender() throws IOException {
        JsonNode rootNode = objectMapper.readTree(userData);
        JsonNode genderNode = rootNode.get("results").get(0).get("gender");

        return genderNode.asText().equalsIgnoreCase(Gender.MALE.getGender()) ? Gender.MALE : Gender.FEMALE;
    }

    public Pictures getPictures() throws IOException {
        JsonNode rootNode = objectMapper.readTree(userData);
        JsonNode picturesNode = rootNode.get("results").get(0).get("picture");

        return new Pictures(picturesNode.get("large").asText(),
                picturesNode.get("medium").asText(),
                picturesNode.get("thumbnail").asText());
    }

    public Title getTitle() throws IOException {
        JsonNode rootNode = objectMapper.readTree(userData);
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
}
