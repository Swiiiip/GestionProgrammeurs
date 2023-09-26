package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

public class DataGenerator {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/APTN61_BD";
        String dbUser = "adm";
        String dbPassword = "adm";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            Random random = new Random();
            int numberOfProgrammers = 1000;
            int numberOfManagers = 50;
            String apiUrl = "https://randomuser.me/api/?inc=name,location";

            // Supprimer toutes les données existantes dans la table Programmeur
            String deleteProgrammerQuery = "DELETE FROM Programmeur";
            PreparedStatement deleteProgrammerStatement = connection.prepareStatement(deleteProgrammerQuery);
            deleteProgrammerStatement.executeUpdate();

            // Supprimer toutes les données existantes dans la table Manager
            String deleteManagerQuery = "DELETE FROM Manager";
            PreparedStatement deleteManagerStatement = connection.prepareStatement(deleteManagerQuery);
            deleteManagerStatement.executeUpdate();

            // Génération des données pour les managers
            String insertManagerQuery = "INSERT INTO Manager (LastName, FirstName, Address, Hobby, Department, BirthYear, Salary, Prime) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatementManager = connection.prepareStatement(insertManagerQuery);

            for (int i = 0; i < numberOfManagers; i++) {
                String jsonData = getJsonDataFromApi(apiUrl);
                String firstName = parseFirstNameFromJson(jsonData);
                String lastName = parseLastNameFromJson(jsonData);
                String address = parseAddressFromJson(jsonData);

                String hobby = Hobbies.generateRandomHobby();
                String department = Departments.generateRandomDepartment();

                int birthYear = random.nextInt(56) + 1950;
                int age = LocalDate.now().getYear() - birthYear;
                // Salaire de base pour les gestionnaires (3000) + augmentation en fonction de l'âge
                float baseSalary = 3000.0f + (age *150.0f);

                float prime = random.nextFloat() * 1000.0f;

                preparedStatementManager.setString(1, lastName);
                preparedStatementManager.setString(2, firstName);
                preparedStatementManager.setString(3, address);
                preparedStatementManager.setString(4, hobby);
                preparedStatementManager.setString(5, department);
                preparedStatementManager.setInt(6, birthYear);
                preparedStatementManager.setFloat(7, baseSalary);
                preparedStatementManager.setFloat(8, prime);

                preparedStatementManager.executeUpdate();
            }

            System.out.println("Données des gestionnaires insérées avec succès.");

            // Génération des données pour les programmeurs
            String insertProgrammerQuery = "INSERT INTO Programmeur (LastName, FirstName, Address, Pseudo, Id_manager, Hobby, BirthYear, Salary, Prime) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatementProgrammer = connection.prepareStatement(insertProgrammerQuery);

            for (int i = 0; i < numberOfProgrammers; i++) {
                String jsonData = getJsonDataFromApi(apiUrl);
                String firstName = parseFirstNameFromJson(jsonData);
                String lastName = parseLastNameFromJson(jsonData);
                String address = parseAddressFromJson(jsonData);

                String hobby = Hobbies.generateRandomHobby();
                int idManager = random.nextInt(numberOfManagers) + 1;
                String pseudo = String.valueOf(firstName.charAt(0) + lastName.charAt(0));
                int birthYear = random.nextInt(56) + 1950;
                int age = LocalDate.now().getYear() - birthYear;
                // Salaire aléatoire pour les programmeurs (en tant que FLOAT)
                float baseSalary = 2000.0f + (age * 75.0f);

                float prime = random.nextFloat() * 500.0f;

                preparedStatementProgrammer.setString(1, lastName);
                preparedStatementProgrammer.setString(2, firstName);
                preparedStatementProgrammer.setString(3, address);
                preparedStatementProgrammer.setString(4, pseudo);
                preparedStatementProgrammer.setInt(5, idManager);
                preparedStatementProgrammer.setString(6, hobby);
                preparedStatementProgrammer.setInt(7, birthYear);
                preparedStatementProgrammer.setFloat(8, baseSalary);
                preparedStatementProgrammer.setFloat(9, prime);

                preparedStatementProgrammer.executeUpdate();
            }

            System.out.println("Données des programmeurs insérées avec succès.");

            preparedStatementManager.close();
            preparedStatementProgrammer.close();
            connection.close();
        } catch (SQLException | IOException | URISyntaxException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String getJsonDataFromApi(String apiUrl) throws IOException, URISyntaxException {
        URI uri = new URI(apiUrl);
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

    private static String parseFirstNameFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");
        return nameNode.get("first").asText();
    }

    private static String parseLastNameFromJson(String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonData);
        JsonNode nameNode = rootNode.get("results").get(0).get("name");
        return nameNode.get("last").asText();
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
}