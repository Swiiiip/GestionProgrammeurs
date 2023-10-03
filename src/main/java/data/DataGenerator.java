package data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import personnes.Manager;
import dao.ManagerDAO;
import personnes.Programmeur;
import dao.ProgrammeurDAO;
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

    private final int NBPROGS;
    private final int NBMANAGERS;

    private final ProgrammeurDAO programmeurDAO = new ProgrammeurDAO();
    private final ManagerDAO managerDAO= new ManagerDAO();
    private static final String APIURL = "https://randomuser.me/api";
    private static final Random RANDOM = new Random();
    //private final static Actions ACTIONSBD = new Actions();

    public DataGenerator(int nbProgs, int nbManagers){
        this.NBPROGS = nbProgs;
        this.NBMANAGERS = nbManagers;
        loadData();
    }
    
    private void loadData(){
        try{
            programmeurDAO.deleteAll();
            managerDAO.deleteAll();
        } catch (SQLException e){
            System.err.println("La suppression de toutes les données a échouée.");
            throw new SecurityException();
        }

        try{
            programmeurDAO.resetIndex();
            managerDAO.resetIndex();
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
                System.err.println("La récupération des données pour le manager " + (i+1) + " a échouée.");
                throw new SecurityException();
            }
            try {
                if (isEuropean(manager.getLastName()) && isEuropean(manager.getFirstName()))
                    i--;
                else {
                    managerDAO.add(manager);
                    System.out.println(getColor() + "Ajout du manager id : " + (i+1) + "\u001B[0m");
                }
            } catch (SQLException e) {
                System.err.println("L'ajout du manager " + (i+1)+ " a échouée.");
                System.out.println(e.getMessage());
                throw new SecurityException();
            }
        }

        for (int i = 0; i < NBPROGS; i++) {
            Programmeur prog;
            try {
                prog = getProgFromAPI();

            } catch (Exception e) {
                System.err.println("La récupération des données pour le programmeur " + (i+1) + " a échouée.");
                throw new SecurityException();
            }
            try {
                if (isEuropean(prog.getLastName()) && isEuropean(prog.getFirstName()))
                    i--;
                else {
                    programmeurDAO.add(prog);
                    System.out.println(getColor() + "Ajout du programmeur id : " + (i+1) + "\u001B[0m");
                }
            } catch (SQLException e) {
                System.err.println("L'ajout du programmeur " + (i+1) + " a échouée.");
                System.out.println(e.getMessage());
                throw new SecurityException();
            }
        }

        programmeurDAO.exit();
        managerDAO.exit();
    }
    private Programmeur getProgFromAPI() throws Exception {
        String jsonData = getJsonDataFromApi();

        String lastName = parseLastNameFromJson(jsonData);
        String firstName = parseFirstNameFromJson(jsonData);
        String gender = parseGenderFromJson(jsonData);
        String picture = parsePictureFromJson(jsonData);
        System.out.println(picture);
        String address = parseAddressFromJson(jsonData);
        String hobby = Hobbies.generateRandomHobby();
        Manager manager = managerDAO.getById(RANDOM.nextInt(NBMANAGERS) + 1);
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
                picture,
                address,
                pseudo,
                manager,
                hobby,
                birthYear,
                salary,
                prime);
    }

    private Manager getManagerFromAPI() throws  Exception {

        String jsonData = getJsonDataFromApi();
        String lastName = parseLastNameFromJson(jsonData);
        String firstName = parseFirstNameFromJson(jsonData);
        String gender = parseGenderFromJson(jsonData);
        String picture = parsePictureFromJson(jsonData);
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
                picture,
                address,
                hobby,
                birthYear,
                salary,
                prime,
                department);
    }

    private String getJsonDataFromApi() throws IOException, URISyntaxException {
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

    private String parseLastNameFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");

        return nameNode.get("last").asText();
    }

    private String parseFirstNameFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");
        
        return nameNode.get("first").asText();
    }


    private String parseAddressFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode locationNode = rootNode.get("results").get(0).get("location");
        int streetNumber = locationNode.get("street").get("number").asInt();
        String streetName = locationNode.get("street").get("name").asText();
        String city = locationNode.get("city").asText();
        String country = locationNode.get("country").asText();

        return streetNumber + " " + streetName + ", " + city + ", " + country;
    }

    private int parseBirthYearFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode birthYearNode = rootNode.get("results").get(0).get("dob");

        int age = birthYearNode.get("age").asInt();

        return LocalDate.now().getYear() - age;
    }

    private String parsePseudoFromJson(String jsonData) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode loginNode = rootNode.get("results").get(0).get("login");

        return loginNode.get("username").asText();
    }

    private String parseGenderFromJson(String jsonData) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode genderNode = rootNode.get("results").get(0).get("gender");

        return genderNode.asText();
    }

    private String parsePictureFromJson(String jsonData) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode pictureNode = rootNode.get("results").get(0).get("picture");
        JsonNode sizeNode = pictureNode.get("large");

        return sizeNode.asText();
    }

    private List<Character> genererCaracteresEuropeens() {
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

    private boolean isEuropean(String texte) {
        List<Character> caracteresEuropeens = genererCaracteresEuropeens();
        for (char c : texte.toCharArray())
            if (!caracteresEuropeens.contains(c))
                return true;

        return false;
    }

    private boolean isWoman(String gender) {
        return gender.equals("female");
    }

    private String getColor(){
        String[] colors = {
                "\u001B[91m",  // Rouge vif
                "\u001B[92m",  // Vert vif
                "\u001B[93m",  // Jaune vif
                "\u001B[94m",  // Bleu vif
                "\u001B[95m",  // Magenta vif
                "\u001B[96m",  // Cyan vif
                "\u001B[97m",  // Blanc vif
        };

        int index = RANDOM.nextInt(colors.length);


        return colors[index];
    }

}