package data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import personnes.Manager;
import personnes.Programmeur;
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
import java.util.*;

public class DataGenerator{

    private static final int NBPROGS = 1000;
    private static final int NBMANAGERS = 50;
    private static final String APIURL = "https://randomuser.me/api";
    private static final Random RANDOM = new Random();
    private final static ActionsBD ACTIONSBD = new ActionsBD();

    public DataGenerator(){
    }
    
    static{
        try{
            ACTIONSBD.deleteALLProgs();
            ACTIONSBD.deleteALLManagers();
        } catch (SQLException e){
            System.err.println("La suppression de toutes les données a échouée.");
            throw new SecurityException();
        }

        try{
            ACTIONSBD.resetIndexProg();
            ACTIONSBD.resetIndexManager();
        } catch (SQLException e){
            System.err.println("La réinitialisation des index à échouée.");
            System.out.println(e.getMessage());
            throw new SecurityException();
        }

        for (int i = 0; i < NBMANAGERS; i++) {
            Manager manager;
            try {
                manager = getManagerFromAPI();
            } catch (Exception e) {
                System.err.println("La récupération des données pour le manager " + i + " a échouée.");
                throw new SecurityException();
            }
            try {
                if (!isEuropean(manager.getLastName()) && !isEuropean(manager.getFirstName()))
                    i--;
                else
                    ACTIONSBD.addManager(manager);
                } catch (SQLException e) {
                System.err.println("L'ajout du manager " + i + " a échouée.");
                System.out.println(e.getMessage());
                throw new SecurityException();
            }
        }

        for (int i = 0; i < NBPROGS; i++) {
            Programmeur prog;
            try {
                prog = getProgFromAPI();

            } catch (Exception e) {
                System.err.println("La récupération des données pour le programmeur " + i + " a échouée.");
                throw new SecurityException();
            }
            try {
                if (!isEuropean(prog.getLastName()) && !isEuropean(prog.getFirstName()))
                    i--;
                else
                    ACTIONSBD.addProg(prog);
            } catch (SQLException e) {
                System.err.println("L'ajout du programmeur " + i + " a échouée.");
                System.out.println(e.getMessage());
                throw new SecurityException();
            }
        }

        ACTIONSBD.exit();

    }

    private static Programmeur getProgFromAPI() throws Exception {
        String jsonData = getJsonDataFromApi();

        String lastName = parseLastNameFromJson(jsonData);
        String firstName = parseFirstNameFromJson(jsonData);
        String gender = parseGenderFromJson(jsonData);
        String address = parseAddressFromJson(jsonData);
        String hobby = Hobbies.generateRandomHobby();
        Manager manager = ACTIONSBD.getManagerById(RANDOM.nextInt(NBMANAGERS) + 1);
        String pseudo = parsePseudoFromJson(jsonData);

        int birthYear = parseBirthYearFromJson(jsonData);
        int age = LocalDate.now().getYear() - birthYear;
        float salary = 2000.0f + (age * 75.0f);
        float prime = RANDOM.nextFloat() * 500.0f;

        if (isWoman(gender))
            salary *= 0.90f;

        return new Programmeur(lastName,
                firstName,
                gender,
                address,
                pseudo,
                manager,
                hobby,
                birthYear,
                salary,
                prime);
    }

    private static Manager getManagerFromAPI() throws  Exception {

        String jsonData = getJsonDataFromApi();
        String lastName = parseLastNameFromJson(jsonData);
        String firstName = parseFirstNameFromJson(jsonData);
        String gender = parseGenderFromJson(jsonData);
        String address = parseAddressFromJson(jsonData);
        int birthYear = parseBirthYearFromJson(jsonData);

        String hobby = Hobbies.generateRandomHobby();
        String department = Departments.generateRandomDepartment();

        int age = LocalDate.now().getYear() - birthYear;

        float salary = 3000.0f + (age *150.0f);
        float prime = RANDOM.nextFloat() * 1000.0f;

        if (isWoman(gender))
            salary *= 0.90f;

        return new Manager(lastName,
                firstName,
                gender,
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

    private static String parseGenderFromJson(String jsonData) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode genderNode = rootNode.get("results").get(0).get("gender");

        return genderNode.asText();
    }

    private static List<Character> genererCaracteresEuropeens() {
        List<Character> caracteresEuropeens = new ArrayList<>();

        for (int codePoint = 0x20; codePoint <= 0x7E; codePoint++) {
            caracteresEuropeens.add((char) codePoint);
        }
        for (int codePoint = 0xC0; codePoint <= 0xFF; codePoint++) {
            caracteresEuropeens.add((char) codePoint);
        }
        for (int codePoint = 0x100; codePoint <= 0x17F; codePoint++) {
            caracteresEuropeens.add((char) codePoint);
        }
        for (int codePoint = 0x180; codePoint <= 0x24F; codePoint++) {
            caracteresEuropeens.add((char) codePoint);
        }

        return caracteresEuropeens;
    }

    private static boolean isEuropean(String texte) {
        List<Character> caracteresEuropeens = genererCaracteresEuropeens();
        for (char c : texte.toCharArray())
            if (!caracteresEuropeens.contains(c))
                return false;

        return true;
    }

    private static boolean isWoman(String gender) {
        return gender.equals("female");
    }

}