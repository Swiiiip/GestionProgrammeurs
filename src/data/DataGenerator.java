package data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import personnes.ManagerBean;
import personnes.ProgrammeurBean;
import utils.Departments;
import utils.Hobbies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

public class DataGenerator {

    private static final int NBPROGS = 1000;
    private static final int NBMANAGERS = 50;
    private static final String APIURL = "https://randomuser.me/api";
    private static final Random RANDOM = new Random();
    private final static ActionsBD ACTIONSBD = new ActionsBD();
    
    static{
        try{
            ACTIONSBD.deleteALLProgs();
            ACTIONSBD.deleteALLManagers();
        } catch (SQLException e){
            System.err.println("La suppression de toutes les données a échouée.");
            throw new SecurityException();
        }

        for (int i = 0; i < NBMANAGERS; i++) {
            ManagerBean manager;
            try {
                manager = getManagerFromAPI();
            } catch (Exception e) {
                System.err.println("La récupération des données pour le manager " + i + " a échouée.");
                throw new SecurityException();
            }
            try {
                ACTIONSBD.addManager(manager);
            } catch (SQLException e) {
                System.err.println("L'ajout du manager " + i + " a échouée.");
                throw new SecurityException();
            }
        }

        for (int i = 0; i < NBPROGS; i++) {
            ProgrammeurBean prog;
            try {
                prog = getProgFromAPI();
            } catch (Exception e) {
                System.err.println("La récupération des données pour le manager " + i + " a échouée.");
                throw new SecurityException();
            }
            try {
                ACTIONSBD.addProg(prog);
            } catch (SQLException e) {
                System.err.println("L'ajout du programmeur " + i + " a échouée.");
                throw new SecurityException();
            }
        }

        ACTIONSBD.exit();

    }

    private static ProgrammeurBean getProgFromAPI() throws Exception {
        String jsonData = getJsonDataFromApi();

        String lastName = parseLastNameFromJson(jsonData);
        String firstName = parseFirstNameFromJson(jsonData);
        String address = parseAddressFromJson(jsonData);
        String hobby = Hobbies.generateRandomHobby();
        ACTIONSBD.getManagerById(RANDOM.nextInt(NBMANAGERS) + 1);
        String pseudo = parsePseudoFromJson(jsonData);

        int birthYear = parseBirthYearFromJson(jsonData);
        int age = LocalDate.now().getYear() - birthYear;
        float salary = 2000.0f + (age * 75.0f);
        float prime = RANDOM.nextFloat() * 500.0f;

        return new ProgrammeurBean(lastName,
                firstName,
                address,
                pseudo,
                new ManagerBean(),
                hobby,
                birthYear,
                salary,
                prime);
    }

    private static ManagerBean getManagerFromAPI() throws  Exception {

        String jsonData = getJsonDataFromApi();
        String lastName = parseLastNameFromJson(jsonData);
        String firstName = parseFirstNameFromJson(jsonData);
        String address = parseAddressFromJson(jsonData);
        int birthYear = parseBirthYearFromJson(jsonData);

        String hobby = Hobbies.generateRandomHobby();
        String department = Departments.generateRandomDepartment();

        int age = LocalDate.now().getYear() - birthYear;

        float salary = 3000.0f + (age *150.0f);
        float prime = RANDOM.nextFloat() * 1000.0f;

        return new ManagerBean(lastName,
                firstName,
                address,
                hobby,
                birthYear,
                salary,
                prime,
                department);
    }

    private static String getJsonDataFromApi() throws IOException, URISyntaxException {
        URI uri = new URI(APIURL);
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return response.toString();
    }

    private static String parseLastNameFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");

        return nameNode.get("last").asText();
    }

    private static String parseFirstNameFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");
        
        return nameNode.get("first").asText();
    }


    private static String parseAddressFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode locationNode = rootNode.get("results").get(0).get("location");
        String street = locationNode.get("street").asText();
        String city = locationNode.get("city").asText();
        String state = locationNode.get("state").asText();
        String postcode = locationNode.get("postcode").asText();
        
        return street + ", " + city + ", " + state + " " + postcode;
    }

    private static int parseBirthYearFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode birthYearNode = rootNode.get("results").get(0).get("dob");

        int age = birthYearNode.get("age").asInt();

        return LocalDate.now().getYear() - age;
    }

    private static String parsePseudoFromJson(String jsonData) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode loginNode = rootNode.get("results").get(0).get("login");

        return loginNode.get("username").asText();
    }
}