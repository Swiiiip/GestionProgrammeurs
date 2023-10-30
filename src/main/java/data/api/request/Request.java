package data.api.request;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Classe abstraite pour la gestion des requêtes HTTP à une API.
 * Cette classe permet de créer des requêtes HTTP GET pour récupérer des données depuis une API en ligne.
 * Elle utilise la bibliothèque Jackson pour la désérialisation des réponses JSON en objets Java.
 *
 * @version 4.7
 * @author Alonso Cédric
 * @author Hatoum Jade
 */
public abstract class Request {

    /**
     * Les données récupérées de la requête HTTP, généralement au format JSON.
     */
    protected String data;

    /**
     * L'objet ObjectMapper de Jackson utilisé pour désérialiser les réponses JSON en objets Java.
     */
    protected ObjectMapper objectMapper;

    /**
     * Constructeur par défaut de la classe Request. Initialise l'objet ObjectMapper avec une configuration
     * qui ignore les propriétés inconnues lors de la désérialisation.
     */
    public Request() {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Génère les données en effectuant une requête HTTP GET à l'URL spécifiée.
     *
     * @param apiurl L'URL de l'API à interroger.
     * @throws Exception En cas d'erreur lors de la génération des données ou de la requête HTTP.
     */
    public void generateData(String apiurl) throws Exception {
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
        this.data = response.toString();
    }
}
